
package com.couchbase.client.core.endpoint.util;


import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufUtil;
import io.netty.handler.codec.compression.DecompressionException;

public final class Snappy {

    private static final int MAX_HT_SIZE = 1 << 14;
    private static final int MIN_COMPRESSIBLE_BYTES = 15;

    private static final int PREAMBLE_NOT_FULL = -1;
    private static final int NOT_ENOUGH_INPUT = -1;

    private static final int LITERAL = 0;
    private static final int COPY_1_BYTE_OFFSET = 1;
    private static final int COPY_2_BYTE_OFFSET = 2;
    private static final int COPY_4_BYTE_OFFSET = 3;

    private Snappy.State state = Snappy.State.READY;
    private byte tag;
    private int written;

    private enum State {
        READY,
        READING_PREAMBLE,
        READING_TAG,
        READING_LITERAL,
        READING_COPY
    }

    public void reset() {
        state = Snappy.State.READY;
        tag = 0;
        written = 0;
    }

    public void encode(final ByteBuf in, final ByteBuf out, final int length) {
        for (int i = 0;; i ++) {
            int b = length >>> i * 7;
            if ((b & 0xFFFFFF80) != 0) {
                out.writeByte(b & 0x7f | 0x80);
            } else {
                out.writeByte(b);
                break;
            }
        }

        int inIndex = in.readerIndex();
        final int baseIndex = inIndex;

        final short[] table = getHashTable(length);
        final int shift = Integer.numberOfLeadingZeros(table.length) + 1;

        int nextEmit = inIndex;

        if (length - inIndex >= MIN_COMPRESSIBLE_BYTES) {
            int nextHash = hash(in, ++inIndex, shift);
            outer: while (true) {
                int skip = 32;

                int candidate;
                int nextIndex = inIndex;
                do {
                    inIndex = nextIndex;
                    int hash = nextHash;
                    int bytesBetweenHashLookups = skip++ >> 5;
                    nextIndex = inIndex + bytesBetweenHashLookups;

                    if (nextIndex > length - 4) {
                        break outer;
                    }

                    nextHash = hash(in, nextIndex, shift);

                    candidate = baseIndex + table[hash];

                    table[hash] = (short) (inIndex - baseIndex);
                }
                while (in.getInt(inIndex) != in.getInt(candidate));

                encodeLiteral(in, out, inIndex - nextEmit);

                int insertTail;
                do {
                    int base = inIndex;
                    int matched = 4 + findMatchingLength(in, candidate + 4, inIndex + 4, length);
                    inIndex += matched;
                    int offset = base - candidate;
                    encodeCopy(out, offset, matched);
                    in.readerIndex(in.readerIndex() + matched);
                    insertTail = inIndex - 1;
                    nextEmit = inIndex;
                    if (inIndex >= length - 4) {
                        break outer;
                    }

                    int prevHash = hash(in, insertTail, shift);
                    table[prevHash] = (short) (inIndex - baseIndex - 1);
                    int currentHash = hash(in, insertTail + 1, shift);
                    candidate = baseIndex + table[currentHash];
                    table[currentHash] = (short) (inIndex - baseIndex);
                }
                while (in.getInt(insertTail + 1) == in.getInt(candidate));

                nextHash = hash(in, insertTail + 2, shift);
                ++inIndex;
            }
        }

        if (nextEmit < length) {
            encodeLiteral(in, out, length - nextEmit);
        }
    }

    private static int hash(ByteBuf in, int index, int shift) {
        return in.getInt(index) * 0x1e35a7bd >>> shift;
    }

    private static short[] getHashTable(int inputSize) {
        int htSize = 256;
        while (htSize < MAX_HT_SIZE && htSize < inputSize) {
            htSize <<= 1;
        }
        return new short[htSize];
    }

    private static int findMatchingLength(ByteBuf in, int minIndex, int inIndex, int maxIndex) {
        int matched = 0;

        while (inIndex <= maxIndex - 4 &&
            in.getInt(inIndex) == in.getInt(minIndex + matched)) {
            inIndex += 4;
            matched += 4;
        }

        while (inIndex < maxIndex && in.getByte(minIndex + matched) == in.getByte(inIndex)) {
            ++inIndex;
            ++matched;
        }

        return matched;
    }

    private static int bitsToEncode(int value) {
        int highestOneBit = Integer.highestOneBit(value);
        int bitLength = 0;
        while ((highestOneBit >>= 1) != 0) {
            bitLength++;
        }

        return bitLength;
    }

    static void encodeLiteral(ByteBuf in, ByteBuf out, int length) {
        if (length < 61) {
            out.writeByte(length - 1 << 2);
        } else {
            int bitLength = bitsToEncode(length - 1);
            int bytesToEncode = 1 + bitLength / 8;
            out.writeByte(59 + bytesToEncode << 2);
            for (int i = 0; i < bytesToEncode; i++) {
                out.writeByte(length - 1 >> i * 8 & 0x0ff);
            }
        }

        out.writeBytes(in, length);
    }

    private static void encodeCopyWithOffset(ByteBuf out, int offset, int length) {
        if (length < 12 && offset < 2048) {
            out.writeByte(COPY_1_BYTE_OFFSET | length - 4 << 2 | offset >> 8 << 5);
            out.writeByte(offset & 0x0ff);
        } else {
            out.writeByte(COPY_2_BYTE_OFFSET | length - 1 << 2);
            out.writeByte(offset & 0x0ff);
            out.writeByte(offset >> 8 & 0x0ff);
        }
    }

    private static void encodeCopy(ByteBuf out, int offset, int length) {
        while (length >= 68) {
            encodeCopyWithOffset(out, offset, 64);
            length -= 64;
        }

        if (length > 64) {
            encodeCopyWithOffset(out, offset, 60);
            length -= 60;
        }

        encodeCopyWithOffset(out, offset, length);
    }

    public void decode(ByteBuf in, ByteBuf out) {
        while (in.isReadable()) {
            switch (state) {
                case READY:
                    state = Snappy.State.READING_PREAMBLE;
                case READING_PREAMBLE:
                    int uncompressedLength = readPreamble(in);
                    if (uncompressedLength == PREAMBLE_NOT_FULL) {
                        return;
                    }
                    if (uncompressedLength == 0) {
                        state = Snappy.State.READY;
                        return;
                    }
                    out.ensureWritable(uncompressedLength);
                    state = Snappy.State.READING_TAG;
                case READING_TAG:
                    if (!in.isReadable()) {
                        return;
                    }
                    tag = in.readByte();
                    switch (tag & 0x03) {
                        case LITERAL:
                            state = Snappy.State.READING_LITERAL;
                            break;
                        case COPY_1_BYTE_OFFSET:
                        case COPY_2_BYTE_OFFSET:
                        case COPY_4_BYTE_OFFSET:
                            state = Snappy.State.READING_COPY;
                            break;
                    }
                    break;
                case READING_LITERAL:
                    int literalWritten = decodeLiteral(tag, in, out);
                    if (literalWritten != NOT_ENOUGH_INPUT) {
                        state = Snappy.State.READING_TAG;
                        written += literalWritten;
                    } else {
                        return;
                    }
                    break;
                case READING_COPY:
                    int decodeWritten;
                    switch (tag & 0x03) {
                        case COPY_1_BYTE_OFFSET:
                            decodeWritten = decodeCopyWith1ByteOffset(tag, in, out, written);
                            if (decodeWritten != NOT_ENOUGH_INPUT) {
                                state = Snappy.State.READING_TAG;
                                written += decodeWritten;
                            } else {
                                return;
                            }
                            break;
                        case COPY_2_BYTE_OFFSET:
                            decodeWritten = decodeCopyWith2ByteOffset(tag, in, out, written);
                            if (decodeWritten != NOT_ENOUGH_INPUT) {
                                state = Snappy.State.READING_TAG;
                                written += decodeWritten;
                            } else {
                                return;
                            }
                            break;
                        case COPY_4_BYTE_OFFSET:
                            decodeWritten = decodeCopyWith4ByteOffset(tag, in, out, written);
                            if (decodeWritten != NOT_ENOUGH_INPUT) {
                                state = Snappy.State.READING_TAG;
                                written += decodeWritten;
                            } else {
                                return;
                            }
                            break;
                    }
            }
        }
    }

    private static int readPreamble(ByteBuf in) {
        int length = 0;
        int byteIndex = 0;
        while (in.isReadable()) {
            int current = in.readUnsignedByte();
            length |= (current & 0x7f) << byteIndex++ * 7;
            if ((current & 0x80) == 0) {
                return length;
            }

            if (byteIndex >= 4) {
                throw new DecompressionException("Preamble is greater than 4 bytes");
            }
        }

        return 0;
    }

    static int decodeLiteral(byte tag, ByteBuf in, ByteBuf out) {
        in.markReaderIndex();
        int length;
        switch(tag >> 2 & 0x3F) {
            case 60:
                if (!in.isReadable()) {
                    return NOT_ENOUGH_INPUT;
                }
                length = in.readUnsignedByte();
                break;
            case 61:
                if (in.readableBytes() < 2) {
                    return NOT_ENOUGH_INPUT;
                }
                length = ByteBufUtil.swapShort(in.readShort());
                break;
            case 62:
                if (in.readableBytes() < 3) {
                    return NOT_ENOUGH_INPUT;
                }
                length = ByteBufUtil.swapMedium(in.readUnsignedMedium());
                break;
            case 63:
                if (in.readableBytes() < 4) {
                    return NOT_ENOUGH_INPUT;
                }
                length = ByteBufUtil.swapInt(in.readInt());
                break;
            default:
                length = tag >> 2 & 0x3F;
        }
        length += 1;

        if (in.readableBytes() < length) {
            in.resetReaderIndex();
            return NOT_ENOUGH_INPUT;
        }

        out.writeBytes(in, length);
        return length;
    }

    private static int decodeCopyWith1ByteOffset(byte tag, ByteBuf in, ByteBuf out, int writtenSoFar) {
        if (!in.isReadable()) {
            return NOT_ENOUGH_INPUT;
        }

        int initialIndex = out.writerIndex();
        int length = 4 + ((tag & 0x01c) >> 2);
        int offset = (tag & 0x0e0) << 8 >> 5 | in.readUnsignedByte();

        validateOffset(offset, writtenSoFar);

        out.markReaderIndex();
        if (offset < length) {
            int copies = length / offset;
            for (; copies > 0; copies--) {
                out.readerIndex(initialIndex - offset);
                out.readBytes(out, offset);
            }
            if (length % offset != 0) {
                out.readerIndex(initialIndex - offset);
                out.readBytes(out, length % offset);
            }
        } else {
            out.readerIndex(initialIndex - offset);
            out.readBytes(out, length);
        }
        out.resetReaderIndex();

        return length;
    }

    private static int decodeCopyWith2ByteOffset(byte tag, ByteBuf in, ByteBuf out, int writtenSoFar) {
        if (in.readableBytes() < 2) {
            return NOT_ENOUGH_INPUT;
        }

        int initialIndex = out.writerIndex();
        int length = 1 + (tag >> 2 & 0x03f);
        int offset = ByteBufUtil.swapShort(in.readShort());

        validateOffset(offset, writtenSoFar);

        out.markReaderIndex();
        if (offset < length) {
            int copies = length / offset;
            for (; copies > 0; copies--) {
                out.readerIndex(initialIndex - offset);
                out.readBytes(out, offset);
            }
            if (length % offset != 0) {
                out.readerIndex(initialIndex - offset);
                out.readBytes(out, length % offset);
            }
        } else {
            out.readerIndex(initialIndex - offset);
            out.readBytes(out, length);
        }
        out.resetReaderIndex();

        return length;
    }

    private static int decodeCopyWith4ByteOffset(byte tag, ByteBuf in, ByteBuf out, int writtenSoFar) {
        if (in.readableBytes() < 4) {
            return NOT_ENOUGH_INPUT;
        }

        int initialIndex = out.writerIndex();
        int length = 1 + (tag >> 2 & 0x03F);
        int offset = ByteBufUtil.swapInt(in.readInt());

        validateOffset(offset, writtenSoFar);

        out.markReaderIndex();
        if (offset < length) {
            int copies = length / offset;
            for (; copies > 0; copies--) {
                out.readerIndex(initialIndex - offset);
                out.readBytes(out, offset);
            }
            if (length % offset != 0) {
                out.readerIndex(initialIndex - offset);
                out.readBytes(out, length % offset);
            }
        } else {
            out.readerIndex(initialIndex - offset);
            out.readBytes(out, length);
        }
        out.resetReaderIndex();

        return length;
    }

    private static void validateOffset(int offset, int chunkSizeSoFar) {
        if (offset > Short.MAX_VALUE) {
            throw new DecompressionException("Offset exceeds maximum permissible value");
        }

        if (offset <= 0) {
            throw new DecompressionException("Offset is less than minimum permissible value");
        }

        if (offset > chunkSizeSoFar) {
            throw new DecompressionException("Offset exceeds size of chunk");
        }
    }

    public static int calculateChecksum(ByteBuf data) {
        return calculateChecksum(data, data.readerIndex(), data.readableBytes());
    }

    public static int calculateChecksum(ByteBuf data, int offset, int length) {
        Crc32c crc32 = new Crc32c();
        try {
            if (data.hasArray()) {
                crc32.update(data.array(), data.arrayOffset() + offset, length);
            } else {
                byte[] array = new byte[length];
                data.getBytes(offset, array);
                crc32.update(array, 0, length);
            }

            return maskChecksum((int) crc32.getValue());
        } finally {
            crc32.reset();
        }
    }

    static void validateChecksum(int expectedChecksum, ByteBuf data) {
        validateChecksum(expectedChecksum, data, data.readerIndex(), data.readableBytes());
    }

    static void validateChecksum(int expectedChecksum, ByteBuf data, int offset, int length) {
        final int actualChecksum = calculateChecksum(data, offset, length);
        if (actualChecksum != expectedChecksum) {
            throw new DecompressionException(
                "mismatching checksum: " + Integer.toHexString(actualChecksum) +
                    " (expected: " + Integer.toHexString(expectedChecksum) + ')');
        }
    }

    static int maskChecksum(int checksum) {
        return (checksum >> 15 | checksum << 17) + 0xa282ead8;
    }
}
