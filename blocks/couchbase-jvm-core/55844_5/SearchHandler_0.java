package com.couchbase.client.core.endpoint.search;

import com.couchbase.client.core.ResponseEvent;
import com.couchbase.client.core.endpoint.AbstractEndpoint;
import com.couchbase.client.core.env.CoreEnvironment;
import com.lmax.disruptor.RingBuffer;
import io.netty.channel.ChannelPipeline;
import io.netty.handler.codec.http.HttpClientCodec;
import io.netty.handler.timeout.IdleStateHandler;

import java.util.concurrent.TimeUnit;

public class SearchEndpoint extends AbstractEndpoint {

    public SearchEndpoint(String hostname, String bucket, String password, int port, CoreEnvironment environment,
                          RingBuffer<ResponseEvent> responseBuffer) {
        super(hostname, bucket, password, port, environment, responseBuffer, false);
    }

    @Override
    protected void customEndpointHandlers(final ChannelPipeline pipeline) {
        if (environment().keepAliveInterval() > 0) {
            pipeline.addLast(new IdleStateHandler(0, 0, environment().keepAliveInterval(), TimeUnit.MILLISECONDS));
        }
        pipeline.addLast(new HttpClientCodec())
                .addLast(new SearchHandler(this, responseBuffer(), false));
    }
}
