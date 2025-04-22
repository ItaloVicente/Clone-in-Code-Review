package com.couchbase.client.java.transcoder;

import com.couchbase.client.core.lang.Tuple2;
import com.couchbase.client.core.message.ResponseStatus;
import com.couchbase.client.deps.io.netty.buffer.ByteBuf;
import com.couchbase.client.deps.io.netty.buffer.Unpooled;
import com.couchbase.client.deps.io.netty.util.CharsetUtil;
import com.couchbase.client.java.document.JsonBooleanDocument;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class JsonBooleanTranscoderTest {

    private JsonBooleanTranscoder converter;

    @Before
    public void setup() {
        converter = new JsonBooleanTranscoder();
    }

    @Test
    public void shouldEncodeTrue() {
        JsonBooleanDocument document = JsonBooleanDocument.create("id", true);
        Tuple2<ByteBuf, Integer> encoded = converter.encode(document);

        assertEquals("true", encoded.value1().toString(CharsetUtil.UTF_8));
        assertEquals(TranscoderUtils.JSON_COMMON_FLAGS, (long) encoded.value2());
    }

    @Test
    public void shouldEncodeFalse() {
        JsonBooleanDocument document = JsonBooleanDocument.create("id", false);
        Tuple2<ByteBuf, Integer> encoded = converter.encode(document);

        assertEquals("false", encoded.value1().toString(CharsetUtil.UTF_8));
        assertEquals(TranscoderUtils.JSON_COMMON_FLAGS, (long) encoded.value2());
    }

    @Test
    public void shouldDecodeTrueFromLegacy() {
        ByteBuf content = Unpooled.buffer().writeChar('1');
        JsonBooleanDocument decoded = converter.decode("id", content, 0, 0, 1 << 8,
            ResponseStatus.SUCCESS);
        assertTrue(decoded.content());
    }

    @Test
    public void shouldDecodeFalseFromLegacy() {
        ByteBuf content = Unpooled.buffer().writeChar('0');
        JsonBooleanDocument decoded = converter.decode("id", content, 0, 0, 1 << 8,
            ResponseStatus.SUCCESS);
        assertFalse(decoded.content());
    }

    @Test
    public void shouldDecodeTrueFromCommonFlags() {
        ByteBuf content = Unpooled.copiedBuffer("true", CharsetUtil.UTF_8);
        JsonBooleanDocument decoded = converter.decode("id", content, 0, 0, TranscoderUtils.JSON_COMMON_FLAGS,
            ResponseStatus.SUCCESS);
        assertTrue(decoded.content());
    }

    @Test
    public void shouldDecodeFalseFromCommonFlags() {
        ByteBuf content = Unpooled.copiedBuffer("false", CharsetUtil.UTF_8);
        JsonBooleanDocument decoded = converter.decode("id", content, 0, 0, TranscoderUtils.JSON_COMMON_FLAGS,
            ResponseStatus.SUCCESS);
        assertFalse(decoded.content());
    }

}
