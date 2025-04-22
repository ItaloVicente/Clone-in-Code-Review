
package com.couchbase.client.core.message;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.util.CharsetUtil;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class ResponseStatusDetailsTest {

    @Test
    public void shouldConvertCorrectDetails() {
        String raw = "{ \"error\" : { \"context\" : \"textual context information\", \"ref\" : \"error reference to be found in the server logs\" }}";
        ByteBuf input = Unpooled.copiedBuffer(raw, CharsetUtil.UTF_8);
        ResponseStatusDetails rsd  = ResponseStatusDetails.convert(input);

        assertEquals("textual context information", rsd.context());
        assertEquals("error reference to be found in the server logs", rsd.reference());
        assertEquals(1, input.refCnt());
    }

    @Test
    public void shouldGracefullyHandleInvalidJSON() {
        String raw = "{ \"err\" : { \"context\" : \"...\", \"ref\" : \"...\" }}";
        ByteBuf input = Unpooled.copiedBuffer(raw, CharsetUtil.UTF_8);
        ResponseStatusDetails rsd  = ResponseStatusDetails.convert(input);
        assertNull(rsd);
    }

    @Test
    public void shouldGracefullyHandleEmptyBuf() {
        ResponseStatusDetails rsd  = ResponseStatusDetails.convert(Unpooled.buffer());
        assertNull(rsd);
    }
}
