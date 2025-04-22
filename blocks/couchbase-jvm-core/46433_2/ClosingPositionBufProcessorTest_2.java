
package com.couchbase.client.core.endpoint.util;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.util.CharsetUtil;
import org.junit.Test;

import static org.junit.Assert.*;

public class ClosingPositionBufProcessorTest {

    @Test
    public void shouldFindClosingInSimpleSection() {
        ByteBuf source = Unpooled.copiedBuffer("{ this is simple }", CharsetUtil.UTF_8);
        int closingPos = source.forEachByte(new ClosingPositionBufProcessor('{', '}'));
        assertEquals(17, closingPos);
        assertEquals(0, source.readerIndex());
    }

    @Test
    public void shouldNotFindClosingInBrokenSection() {
        ByteBuf source = Unpooled.copiedBuffer("{ this is simple", CharsetUtil.UTF_8);
        int closingPos = source.forEachByte(new ClosingPositionBufProcessor('{', '}'));
        assertEquals(-1, closingPos);
        assertEquals(0, source.readerIndex());
    }

    @Test
    public void shouldFindClosingInSectionWithSubsection() {
        ByteBuf source = Unpooled.copiedBuffer("{ this is { simple } }", CharsetUtil.UTF_8);
        int closingPos = source.forEachByte(new ClosingPositionBufProcessor('{', '}'));
        assertEquals(21, closingPos);
        assertEquals(0, source.readerIndex());
    }

    @Test
    public void shouldNotFindClosingInBrokenSectionWithCompleteSubSection() {
        ByteBuf source = Unpooled.copiedBuffer("{ this is { complex } oups", CharsetUtil.UTF_8);
        int closingPos = source.forEachByte(new ClosingPositionBufProcessor('{', '}'));
        assertEquals(-1, closingPos);
        assertEquals(0, source.readerIndex());
    }
}
