package load.direct;

import com.codahale.metrics.Timer;
import com.datastax.driver.core.BatchStatement;
import com.datastax.driver.core.BoundStatement;
import com.datastax.driver.core.PreparedStatement;
import com.google.common.collect.Iterators;
import load.domain.InfareRecord;
import load.input.InfareInputSource;

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
                .parallel()
                .map(list -> {
                    final Timer.Context batchGenerateTime = Metrics.batchGenerateTime.time();
                    try {
                        final BatchStatement batchStatement = new BatchStatement(BatchStatement.Type.UNLOGGED);
                        final List<BoundStatement> statements = list.stream()
                                .map(record -> record.bindToStatement(preparedStatement))
                                .collect(Collectors.toList());

                        batchStatement.addAll(statements);
                        batchStatement.setRetryPolicy(new LoaderRetryPolicy(20));
                        return batchStatement;
                    } finally {
                        batchGenerateTime.stop();
                    }
                });
    }

    private Stream<List<InfareRecord>> stream() {
        return StreamSupport.stream(spliterator(), true);
    }

    private Spliterator<List<InfareRecord>> spliterator() {
        return Spliterators.spliteratorUnknownSize(
                Iterators.partition(inputSource.get().iterator(), batchSize),
                Spliterator.CONCURRENT);
    }
}
