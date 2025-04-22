package com.couchbase.client.core.endpoint.query;

import com.couchbase.client.core.cluster.ResponseEvent;
import com.couchbase.client.core.endpoint.AbstractEndpoint;
import com.couchbase.client.core.env.Environment;
import com.lmax.disruptor.RingBuffer;
import io.netty.channel.ChannelPipeline;
import io.netty.handler.codec.http.HttpClientCodec;
import io.netty.handler.codec.http.HttpObjectAggregator;

public class QueryEndpoint extends AbstractEndpoint {

    public QueryEndpoint(String hostname, String bucket, String password, int port, Environment environment,
        RingBuffer<ResponseEvent> responseBuffer) {
        super(hostname, bucket, password, port, environment, responseBuffer);
    }

    @Override
    protected void customEndpointHandlers(final ChannelPipeline pipeline) {
        pipeline
            .addLast(new HttpClientCodec())
            .addLast(new HttpObjectAggregator(Integer.MAX_VALUE))
            .addLast(new QueryCodec());
    }
}
