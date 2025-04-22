
package com.couchbase.client.core.endpoint.kv;

import com.couchbase.client.deps.io.netty.handler.codec.memcache.binary.BinaryMemcacheRequest;
import com.couchbase.client.deps.io.netty.handler.codec.memcache.binary.DefaultFullBinaryMemcacheResponse;
import com.couchbase.client.deps.io.netty.handler.codec.memcache.binary.FullBinaryMemcacheRequest;
import com.couchbase.client.deps.io.netty.handler.codec.memcache.binary.FullBinaryMemcacheResponse;
import io.netty.buffer.Unpooled;
import io.netty.channel.embedded.EmbeddedChannel;
import io.netty.util.CharsetUtil;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class KeyValueAuthHandlerTest {

    @Test
    public void shouldAlwaysPickPlainIfForced() {
        KeyValueAuthHandler handler = new KeyValueAuthHandler("user", "pass", true);
        EmbeddedChannel channel = new EmbeddedChannel(handler);

        BinaryMemcacheRequest listMechsRequest = (BinaryMemcacheRequest) channel.readOutbound();
        assertEquals(KeyValueAuthHandler.SASL_LIST_MECHS_OPCODE, listMechsRequest.getOpcode());

        FullBinaryMemcacheResponse listMechsResponse = new DefaultFullBinaryMemcacheResponse(
                new byte[] {},
                Unpooled.EMPTY_BUFFER,
                Unpooled.copiedBuffer("CRAM-MD5 PLAIN", CharsetUtil.UTF_8)
        );
        listMechsResponse.setOpcode(KeyValueAuthHandler.SASL_LIST_MECHS_OPCODE);
        channel.writeInbound(listMechsResponse);

        FullBinaryMemcacheRequest initialRequest = (FullBinaryMemcacheRequest) channel.readOutbound();
        assertEquals("PLAIN", new String(initialRequest.getKey()));
    }

    @Test
    public void shouldPickHighestMechIfNotForced() {
        KeyValueAuthHandler handler = new KeyValueAuthHandler("user", "pass", false);
        EmbeddedChannel channel = new EmbeddedChannel(handler);

        BinaryMemcacheRequest listMechsRequest = (BinaryMemcacheRequest) channel.readOutbound();
        assertEquals(KeyValueAuthHandler.SASL_LIST_MECHS_OPCODE, listMechsRequest.getOpcode());

        FullBinaryMemcacheResponse listMechsResponse = new DefaultFullBinaryMemcacheResponse(
                new byte[] {},
                Unpooled.EMPTY_BUFFER,
                Unpooled.copiedBuffer("SCRAM-SHA1 CRAM-MD5 PLAIN", CharsetUtil.UTF_8)
        );
        listMechsResponse.setOpcode(KeyValueAuthHandler.SASL_LIST_MECHS_OPCODE);
        channel.writeInbound(listMechsResponse);

        FullBinaryMemcacheRequest initialRequest = (FullBinaryMemcacheRequest) channel.readOutbound();
        assertEquals("SCRAM-SHA1", new String(initialRequest.getKey()));
    }
}
