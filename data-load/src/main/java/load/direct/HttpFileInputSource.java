package load.direct;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URI;
import java.util.stream.Stream;
import java.util.zip.GZIPInputStream;

public class HttpFileInputSource implements InfareInputSource, AutoCloseable {

    private final String url;
    private InputStream httpInputStream;
    private BufferedReader bufferedReader;

    public HttpFileInputSource(String url) {
        this.url = url;
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
        httpInputStream = new URI(url).toURL().openStream();
        bufferedReader = new BufferedReader(new InputStreamReader(new GZIPInputStream(httpInputStream)));

        return bufferedReader
                .lines()
                .map(InfareRecord::fromLine);
    }

    @Override
    public void close() throws Exception {
        bufferedReader.close();
        httpInputStream.close();
    }
}
