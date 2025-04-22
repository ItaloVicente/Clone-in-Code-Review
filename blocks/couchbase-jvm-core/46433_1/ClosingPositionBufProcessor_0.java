
package com.couchbase.client.core.endpoint.util;

import io.netty.buffer.ByteBuf;

public class ByteBufJsonHelper {


    public static final int findNextChar(ByteBuf buf, char c) {
        return buf.bytesBefore((byte) c);
    }

    public static final int findNextCharNotPrefixedBy(ByteBuf buf, char c, char prefix) {
        int found =  buf.bytesBefore((byte) c);
        if (found < 1) {
            return found;
        } else {
            int from;
            while (found > -1 && (char) buf.getByte(
                    buf.readerIndex() + found - 1) == prefix) {
                from = buf.readerIndex() + found + 1;
                int next = buf.bytesBefore(from, buf.readableBytes() - from, (byte) c);
                if (next == -1) {
                    return -1;
                } else {
                    found += next + 1;
                }
            }
            return found;
        }
    }

    public static int findSectionClosingPosition(ByteBuf buf, char openingChar, char closingChar) {
        return buf.forEachByte(new ClosingPositionBufProcessor(openingChar, closingChar));
    }

    public static int findSectionClosingPosition(ByteBuf buf, int startOffset, char openingChar, char closingChar) {
        int from = buf.readerIndex() + startOffset;
        int length = buf.writerIndex() - from;
        if (length < 0) {
            throw new IllegalArgumentException("startOffset must not go beyond the readable byte length of the buffer");
        }

        return buf.forEachByte(from, length,
                new ClosingPositionBufProcessor(openingChar, closingChar));
    }
}
