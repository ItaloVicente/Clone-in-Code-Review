package com.couchbase.client.java.transcoder;

import com.couchbase.client.core.lang.Tuple2;
import com.couchbase.client.core.message.ResponseStatus;
import com.couchbase.client.deps.io.netty.buffer.ByteBuf;
import com.couchbase.client.deps.io.netty.buffer.Unpooled;
import com.couchbase.client.deps.io.netty.util.CharsetUtil;
import com.couchbase.client.java.document.JsonLongDocument;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class JsonLongTranscoderTest {

    private JsonLongTranscoder converter;

    @Before
    public void setup() {
        converter = new JsonLongTranscoder();
    }

    @Test
    public void shouldEncodeLong() {
        JsonLongDocument doc = JsonLongDocument.create("id", Long.MAX_VALUE);
        Tuple2<ByteBuf, Integer> encoded = converter.encode(doc);

        assertEquals("9223372036854775807", encoded.value1().toString(CharsetUtil.UTF_8));
        assertEquals(TranscoderUtils.JSON_COMPAT_FLAGS, (long) encoded.value2());
    }

    @Test
    public void shouldDecodeLegacyLong() {
        byte[] bytes = LegacyTranscoder.encodeNum(Long.MAX_VALUE, 8);
        ByteBuf content = Unpooled.buffer().writeBytes(bytes);
        JsonLongDocument decoded = converter.decode("id", content, 0, 0, 3 << 8,
            ResponseStatus.SUCCESS);

        assertEquals(Long.MAX_VALUE, (long) decoded.content());
    }

    @Test
    public void shouldDecodeLegacyInt() {
        byte[] bytes = LegacyTranscoder.encodeNum(Integer.MAX_VALUE, 4);
        ByteBuf content = Unpooled.buffer().writeBytes(bytes);
        JsonLongDocument decoded = converter.decode("id", content, 0, 0, 2 << 8,
            ResponseStatus.SUCCESS);

        assertEquals(Integer.MAX_VALUE, (long) decoded.content());
    }

    @Test
    public void shouldDecodeCommonFlagsLong() {
        ByteBuf content = Unpooled.copiedBuffer("9223372036854775807", CharsetUtil.UTF_8);
        JsonLongDocument decoded = converter.decode("id", content, 0, 0, TranscoderUtils.JSON_COMPAT_FLAGS,
            ResponseStatus.SUCCESS);

        assertEquals(Long.MAX_VALUE, (long) decoded.content());
    }

    @Test
    public void shouldDecodeCommonFlagsInt() {
        ByteBuf content = Unpooled.copiedBuffer("2147483647", CharsetUtil.UTF_8);
        JsonLongDocument decoded = converter.decode("id", content, 0, 0, TranscoderUtils.JSON_COMPAT_FLAGS,
            ResponseStatus.SUCCESS);

        assertEquals(Integer.MAX_VALUE, (long) decoded.content());
    }
}
