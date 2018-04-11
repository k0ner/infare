package load.direct.aws;

import java.util.stream.Stream;

public interface InfareInputSource {

    Stream<InfareRecord> get();
}
