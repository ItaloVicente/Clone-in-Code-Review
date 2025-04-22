
package com.couchbase.client.core.endpoint.analytics;

import com.couchbase.client.core.ResponseEvent;
import com.couchbase.client.core.endpoint.AbstractEndpoint;
import com.couchbase.client.core.env.CoreEnvironment;
import com.lmax.disruptor.RingBuffer;
import io.netty.channel.ChannelPipeline;
import io.netty.handler.codec.http.HttpClientCodec;
import io.netty.handler.timeout.IdleStateHandler;

import java.util.concurrent.TimeUnit;

public class AnalyticsEndpoint extends AbstractEndpoint {

    public AnalyticsEndpoint(String hostname, String bucket, String password, int port, CoreEnvironment environment,
                         RingBuffer<ResponseEvent> responseBuffer) {
        super(hostname, bucket, password, port, environment, responseBuffer, false,
                environment.queryIoPool() == null ? environment.ioPool() : environment.queryIoPool(), false);
    }

    @Override
    protected void customEndpointHandlers(final ChannelPipeline pipeline) {
        if (environment().keepAliveInterval() > 0) {
            pipeline.addLast(new IdleStateHandler(environment().keepAliveInterval(), 0, 0, TimeUnit.MILLISECONDS));
        }
        pipeline
                .addLast(new HttpClientCodec())
                .addLast(new AnalyticsHandler(this, responseBuffer(), false, false));
    }
}
