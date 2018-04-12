package load.input;

import load.domain.InfareRecord;

import java.util.stream.Stream;

public interface InfareInputSource extends AutoCloseable {

    Stream<InfareRecord> get();
}
