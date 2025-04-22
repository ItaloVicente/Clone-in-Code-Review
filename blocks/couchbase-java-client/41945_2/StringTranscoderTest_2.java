package com.couchbase.client.java.transcoder;

import com.couchbase.client.core.lang.Tuple2;
import com.couchbase.client.core.message.ResponseStatus;
import com.couchbase.client.deps.io.netty.buffer.ByteBuf;
import com.couchbase.client.deps.io.netty.buffer.Unpooled;
import com.couchbase.client.deps.io.netty.util.CharsetUtil;
import com.couchbase.client.java.document.RawJsonDocument;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class RawJsonTranscoderTest {

    private RawJsonTranscoder converter;

    @Before
    public void setup() {
        converter = new RawJsonTranscoder();
    }

    @Test
    public void shouldEncodeRawJson() {
        RawJsonDocument document = RawJsonDocument.create("id", "{\"test:\":true}");
        Tuple2<ByteBuf, Integer> encoded = converter.encode(document);

        assertEquals("{\"test:\":true}", encoded.value1().toString(CharsetUtil.UTF_8));
        assertEquals(TranscoderUtils.JSON_COMPAT_FLAGS, (long) encoded.value2());
    }

    @Test
    public void shouldDecodeRawJson() {
        ByteBuf content = Unpooled.copiedBuffer("{\"test:\":true}", CharsetUtil.UTF_8);
        RawJsonDocument decoded = converter.decode("id", content, 0, 0, TranscoderUtils.JSON_COMPAT_FLAGS,
            ResponseStatus.SUCCESS);

        assertEquals("{\"test:\":true}", decoded.content());
    }

}
