
package com.couchbase.client.core.endpoint.dcp;

import com.couchbase.client.core.ResponseEvent;
import com.couchbase.client.core.endpoint.AbstractEndpoint;
import com.couchbase.client.core.endpoint.kv.KeyValueAuthHandler;
import com.couchbase.client.core.env.CoreEnvironment;
import com.couchbase.client.deps.io.netty.handler.codec.memcache.binary.BinaryMemcacheClientCodec;
import com.couchbase.client.deps.io.netty.handler.codec.memcache.binary.BinaryMemcacheObjectAggregator;
import com.lmax.disruptor.RingBuffer;
import io.netty.channel.ChannelPipeline;

public class DCPEndpoint extends AbstractEndpoint {

    public DCPEndpoint(String hostname, String bucket, String password, int port,
                       CoreEnvironment environment, RingBuffer<ResponseEvent> responseBuffer) {
        super(hostname, bucket, password, port, environment, responseBuffer);
    }

    @Override
    protected void customEndpointHandlers(ChannelPipeline pipeline) {
        pipeline
                .addLast(new BinaryMemcacheClientCodec())
                .addLast(new BinaryMemcacheObjectAggregator(Integer.MAX_VALUE))
                .addLast(new KeyValueAuthHandler(bucket(), password()))
                .addLast(new DCPHandler(this, responseBuffer()));

    }
}
