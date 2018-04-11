package load.direct;

import java.util.stream.Stream;

public interface InfareInputSource {

    Stream<InfareRecord> get();
}
