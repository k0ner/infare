package load.input;

import load.domain.InfareRecord;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Path;
import java.util.stream.Stream;

public class FileInputSource implements InfareInputSource {

    private final Path path;
    private InputStream inputStream;
    private BufferedReader bufferedReader;

    FileInputSource(Path path) {
        this.path = path;
    }

    @Override
    public Stream<InfareRecord> get() {
        try {
            return httpStream();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private Stream<InfareRecord> httpStream() throws Exception {
        inputStream = new FileInputStream(path.toFile());
        bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

        return bufferedReader
                .lines()
                .map(InfareRecord::fromLine);
    }

    @Override
    public void close() throws Exception {
        bufferedReader.close();
        inputStream.close();
    }
}
