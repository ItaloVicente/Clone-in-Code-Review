
package com.couchbase.client.core.endpoint.util;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.util.CharsetUtil;
import org.junit.Test;

import static org.junit.Assert.*;

public class StringClosingPositionBufProcessorTest {

    @Test
    public void testClosingPosFoundInSimpleString() {
        ByteBuf source = Unpooled.copiedBuffer("\" \"", CharsetUtil.UTF_8);

        int closingPos = source.forEachByte(new StringClosingPositionBufProcessor());

        assertEquals(2, closingPos);
        assertEquals(0, source.readerIndex());
    }

    @Test
    public void testClosingPosFoundInStringWithEscapedContent() {
        ByteBuf source = Unpooled.copiedBuffer(" \"Some string with {\\\"escaped\\\"} strings\" \"otherString\"",
                CharsetUtil.UTF_8);

        int closingPos = source.forEachByte(new StringClosingPositionBufProcessor());

        assertEquals(40, closingPos);
        assertEquals(0, source.readerIndex());
    }

    @Test
    public void testClosingPosNotFoundInPartialStringLeftPart() {
        ByteBuf source = Unpooled.copiedBuffer(" \"\\\"Partial\\\" str",
                CharsetUtil.UTF_8);

        int closingPos = source.forEachByte(new StringClosingPositionBufProcessor());

        assertEquals(-1, closingPos);
        assertEquals(0, source.readerIndex());
    }

    @Test
    public void testClosingPosNotFoundInPartialStringRightPart() {
        ByteBuf source = Unpooled.copiedBuffer("ring\"",
                CharsetUtil.UTF_8);

        int closingPos = source.forEachByte(new StringClosingPositionBufProcessor());

        assertEquals(-1, closingPos);
        assertEquals(0, source.readerIndex());
    }
}
