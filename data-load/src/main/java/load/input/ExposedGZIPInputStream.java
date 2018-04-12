package load.input;

import java.io.IOException;
import java.io.InputStream;
import java.util.zip.GZIPInputStream;
import java.util.zip.Inflater;

public final class ExposedGZIPInputStream extends GZIPInputStream {

    public ExposedGZIPInputStream(final InputStream stream) throws IOException {
        super(stream);
    }

    public ExposedGZIPInputStream(final InputStream stream, final int n) throws IOException {
        super(stream, n);
    }

    public Inflater inflater() {
        return super.inf;
    }
}
