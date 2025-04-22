package com.couchbase.client.core;

import com.couchbase.client.core.env.CoreEnvironment;
import com.couchbase.client.core.env.DefaultCoreEnvironment;
import com.couchbase.client.core.event.CouchbaseEvent;
import com.couchbase.client.core.event.system.TooManyEnvironmentsEvent;
import org.junit.Test;
import rx.functions.Action1;

import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class ManyEnvironmentsTest {

    @Test
    public void shouldEmitEvent() {
        CoreEnvironment env = DefaultCoreEnvironment.create();

        final AtomicInteger evtCount = new AtomicInteger(0);
        env.eventBus().get().forEach(new Action1<CouchbaseEvent>() {
            @Override
            public void call(CouchbaseEvent couchbaseEvent) {
                if (couchbaseEvent instanceof TooManyEnvironmentsEvent) {
                    evtCount.set(((TooManyEnvironmentsEvent) couchbaseEvent).numEnvs());
                }
            }
        });

        CoreEnvironment env2 = DefaultCoreEnvironment.builder().eventBus(env.eventBus()).build();

        env.shutdown();
        env2.shutdown();

        assertTrue(evtCount.get() > 1);
    }

}
