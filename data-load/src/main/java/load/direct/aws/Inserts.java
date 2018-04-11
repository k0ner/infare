package load.direct.aws;

import com.google.common.collect.Iterators;

import com.datastax.driver.core.BatchStatement;
import com.datastax.driver.core.BoundStatement;
import com.datastax.driver.core.PreparedStatement;

import java.util.List;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public class Inserts {

    private final InfareInputSource inputSource;
    private final int batchSize;
    private final PreparedStatement preparedStatement;

    public Inserts(InfareInputSource inputSource,
                   int batchSize,
                   PreparedStatement preparedStatement) {
        this.inputSource = inputSource;
        this.batchSize = batchSize;
        this.preparedStatement = preparedStatement;
    }

    Stream<BatchStatement> generate() {
        return stream()
                .map(list -> {
                    final BatchStatement batchStatement = new BatchStatement(BatchStatement.Type.UNLOGGED);
                    final List<BoundStatement> statements = list.stream()
                            .map(record -> record.bindToStatement(preparedStatement))
                            .collect(Collectors.toList());

                    batchStatement.addAll(statements);
                    return batchStatement;
                });
    }

    private Stream<List<InfareRecord>> stream() {
        return StreamSupport.stream(spliterator(), false);
    }

    private Spliterator<List<InfareRecord>> spliterator() {
        return Spliterators.spliteratorUnknownSize(
                Iterators.partition(inputSource.get().iterator(), batchSize),
                Spliterator.ORDERED);
    }
}
