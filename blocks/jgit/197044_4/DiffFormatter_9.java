
package org.eclipse.jgit.binarydiff;

import java.io.IOException;
import java.nio.ByteBuffer;

public class SeekableSource {

    private ByteBuffer sourceBuffer;
    private ByteBuffer currentBuffer;

    public SeekableSource(byte[] source) {
        this(ByteBuffer.wrap(source));
    }

    public SeekableSource(ByteBuffer sourceBuffer) {
        if (sourceBuffer == null) {
        }
        this.sourceBuffer = sourceBuffer;
        sourceBuffer.rewind();
        try {
            seek(0);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void seek(long position) throws IOException {
        currentBuffer = sourceBuffer.slice();
        if (position > currentBuffer.limit()) {
            throw new IOException("The position" + position +
        }
        currentBuffer.position((int)position);
    }

    public int read(ByteBuffer destination) {
        if (!currentBuffer.hasRemaining()) {
            return -1;
        }
        int byteNumber = 0;
        while (currentBuffer.hasRemaining() && destination.hasRemaining()) {
            destination.put(currentBuffer.get());
            byteNumber++;
        }
        return byteNumber;
    }

    public void close() {
        sourceBuffer = null;
        currentBuffer = null;
    }

}
