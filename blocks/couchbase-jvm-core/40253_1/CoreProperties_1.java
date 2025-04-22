package com.couchbase.client.core.env;

import io.netty.channel.EventLoopGroup;
import rx.Observable;
import rx.Scheduler;

public interface CoreEnvironment {

    Observable<Boolean> shutdown();

    EventLoopGroup ioPool();

    CoreProperties properties();

    Scheduler scheduler();
}
