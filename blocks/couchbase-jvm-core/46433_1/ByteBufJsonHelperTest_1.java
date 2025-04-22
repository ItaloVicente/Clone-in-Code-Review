
package com.couchbase.client.core.endpoint.util;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.util.CharsetUtil;
import org.junit.Test;

import static org.junit.Assert.*;

public class ByteBufJsonHelperTest {

    @Test
    public void testFindNextChar() throws Exception {
        ByteBuf source = Unpooled.copiedBuffer("ABCDEF", CharsetUtil.UTF_8);

        assertEquals(3, ByteBufJsonHelper.findNextChar(source, 'D'));
        assertEquals(-1, ByteBufJsonHelper.findNextChar(source, 'G'));
        assertEquals(0, source.readerIndex());

    }

    @Test
    public void testFindNextCharNotPrefixedBy() throws Exception {
        ByteBuf source = Unpooled.copiedBuffer("the \\\"end\\\" of a string\"", CharsetUtil.UTF_8);

        assertEquals(5, ByteBufJsonHelper.findNextCharNotPrefixedBy(source, '\"', 'a'));
        assertEquals(23, ByteBufJsonHelper.findNextCharNotPrefixedBy(source, '\"', '\\'));
        assertEquals(-1, ByteBufJsonHelper.findNextCharNotPrefixedBy(source, 'a', ' '));
        assertEquals(-1, ByteBufJsonHelper.findNextCharNotPrefixedBy(source, 'z', ' '));
        assertEquals(0, source.readerIndex());
    }

    @Test
    public void shouldFindSectionClosingPositionIfAtStartSection() throws Exception {
        ByteBuf source = Unpooled.copiedBuffer("{123}", CharsetUtil.UTF_8);

        assertEquals(4, ByteBufJsonHelper.findSectionClosingPosition(source, '{', '}'));
        assertEquals(0, source.readerIndex());
    }

    @Test
    public void shouldNotFindSectionClosingPositionIfAfterStartSection() throws Exception {
        ByteBuf source = Unpooled.copiedBuffer("123}", CharsetUtil.UTF_8);

        assertEquals(-1, ByteBufJsonHelper.findSectionClosingPosition(source, '{', '}'));
        assertEquals(0, source.readerIndex());
    }

    @Test
    public void shouldFindSectionClosingPositionWithOffset() throws Exception {
        ByteBuf source = Unpooled.copiedBuffer("{{{}{123}", CharsetUtil.UTF_8);

        assertEquals(8, ByteBufJsonHelper.findSectionClosingPosition(source, 4, '{', '}'));
        assertEquals(0, source.readerIndex());
    }

    @Test
    public void shouldFindSectionClosingInRealLifeExample() {
        ByteBuf source = Unpooled.copiedBuffer("rics\":{\"elapsedTime\":\"128.21463ms\",\"executionTime\":\"127.21463ms\",\"resultCount\":1,\"resultSize\":0,\"mutationCount\":0,\"errorCount\":0,\"warningCount\":0}}",
                CharsetUtil.UTF_8);

        assertNotEquals(-1, ByteBufJsonHelper.findSectionClosingPosition(source, '{', '}'));
    }

}
