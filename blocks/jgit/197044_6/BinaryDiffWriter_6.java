
package org.eclipse.jgit.binarydiff;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;

public class BinaryDelta {

    public static final int DEFAULT_CHUNK_SIZE = 1 << 4;

    private int chunk;
    private TargetState target;
    private BinaryDiffWriter output;

    public BinaryDelta(OutputStream output) {
        this.output = new BinaryDiffWriter(output);
        setChunkSize(DEFAULT_CHUNK_SIZE);
    }

    public void setChunkSize(int size) {
        if (size <= 0) {
        }
        chunk = size;
    }

    public void compute(byte[] source
        compute(new SeekableSource(source)
                new ByteArrayInputStream(target)
    }

    public void compute(byte[] sourceBytes
            throws IOException {
        compute(new SeekableSource(sourceBytes)
    }

    public void compute(SeekableSource seekSource
                        InputStream targetStream
            throws IOException {
        this.output = output;
        SourceState source = new SourceState(seekSource);
        target = new TargetState(targetStream);
        while (!target.eof()) {
            long index = target.find(source);
            if (index != -1) {
                long offset = index * chunk;
                source.seek(offset);
                int match = target.longestMatch(source);
                if (match >= chunk) {
                    output.addCopy(offset
                } else {
                    target.targetBuffer.position(
                            target.targetBuffer.position() - match);
                    addData();
                }
            } else {
                addData();
            }
        }
        output.close();
    }

    private void addData() throws IOException {
        int i = target.read();
        if (i == -1) {
            return;
        }
        output.addData((byte)i);
    }

    public void writeFileSize(long fileSize)  throws IOException {
        output.writeFileSize(fileSize);
    }

    class SourceState {

        private final Checksum checksum;
        private final SeekableSource source;

        private SourceState(SeekableSource source) throws IOException {
            checksum = new Checksum(source
            this.source = source;
            source.seek(0);
        }

        private void seek(long index) throws IOException {
            source.seek(index);
        }

    }

    class TargetState {

        private final ReadableByteChannel byteChannel;

        private final ByteBuffer targetBuffer = ByteBuffer.allocate(
                blockSize());
        private final ByteBuffer sourceBuffer = ByteBuffer.allocate(
                blockSize());

        private long hash;
        private boolean hashReset = true;
        private boolean eof;

        private TargetState(InputStream targetInputStream) {
            byteChannel = Channels.newChannel(targetInputStream);
            targetBuffer.limit(0);
        }

        private int blockSize() {
            return Math.min(1024 * 8
        }

        private long find(SourceState source) throws IOException {
            if (eof) {
                return -1;
            }
            sourceBuffer.clear();
            sourceBuffer.limit(0);
            if (hashReset) {
                while (targetBuffer.remaining() < chunk) {
                    targetBuffer.compact();
                    int read = byteChannel.read(targetBuffer);
                    targetBuffer.flip();
                    if (read == -1) {
                        return -1;
                    }
                }
                hash = Checksum.queryChecksum(targetBuffer
                hashReset = false;
            }
            return source.checksum.findChecksumIndex(hash);
        }

        private boolean eof() {
            return eof;
        }

        private int read() throws IOException {
            if (targetBuffer.remaining() <= chunk) {
                readMore();
                if (!targetBuffer.hasRemaining()) {
                    eof = true;
                    return -1;
                }
            }
            byte b = targetBuffer.get();
            if (targetBuffer.remaining() >= chunk) {
                byte newByte = targetBuffer.get(targetBuffer.position() +
                        chunk -1);
                hash = Checksum.incrementChecksum(hash
            }
            return b & 0xFF;
        }

        private int longestMatch(SourceState source) throws IOException {
            int match = 0;
            hashReset = true;
            while (true) {
                if (!sourceBuffer.hasRemaining()) {
                    sourceBuffer.clear();
                    int read = source.source.read(sourceBuffer);
                    sourceBuffer.flip();
                    if (read == -1) {
                        return match;
                    }
                }
                if (!targetBuffer.hasRemaining()) {
                    readMore();
                    if (!targetBuffer.hasRemaining()) {
                        eof = true;
                        return match;
                    }
                }
                if (sourceBuffer.get() != targetBuffer.get()) {
                    targetBuffer.position(
                            targetBuffer.position() - 1);
                    return match;
                }
                match++;
            }
        }

        private void readMore() throws IOException {
            targetBuffer.compact();
            byteChannel.read(targetBuffer);
            targetBuffer.flip();
        }
    }

}
