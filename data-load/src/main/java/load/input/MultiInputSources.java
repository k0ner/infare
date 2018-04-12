package load.input;

import load.domain.InfareRecord;

import java.util.List;
import java.util.stream.Stream;

public class MultiInputSources implements InfareInputSource {

    private final List<InfareInputSource> infareInputSources;

    public MultiInputSources(List<InfareInputSource> infareInputSources) {
        this.infareInputSources = infareInputSources;
    }

    @Override
    public void close() throws Exception {
        infareInputSources
                .forEach(infareInputSource -> {
                    try {
                        infareInputSource.close();
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }

                });
    }

    @Override
    public Stream<InfareRecord> get() {
        return infareInputSources
                .stream()
                .flatMap(InfareInputSource::get);
    }
}
