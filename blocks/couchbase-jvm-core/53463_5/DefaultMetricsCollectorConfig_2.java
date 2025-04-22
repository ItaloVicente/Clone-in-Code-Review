package com.couchbase.client.core.metrics;

import com.couchbase.client.core.event.CouchbaseEvent;
import com.couchbase.client.core.event.EventBus;
import com.couchbase.client.core.logging.CouchbaseLogger;
import com.couchbase.client.core.logging.CouchbaseLoggerFactory;
import rx.Observable;
import rx.Scheduler;
import rx.Subscription;
import rx.functions.Action1;

public abstract class AbstractMetricsCollector implements MetricsCollector {

    private static final CouchbaseLogger LOGGER = CouchbaseLoggerFactory.getInstance(MetricsCollector.class);

    private final MetricsCollectorConfig config;
    private final Subscription subscription;

    protected AbstractMetricsCollector(final EventBus eventBus, Scheduler scheduler, MetricsCollectorConfig config) {
        this.config = config;

        if (config.emitFrequency() > 0) {
            subscription = Observable
                    .interval(config.emitFrequency(), config.emitFrequencyUnit(), scheduler)
                    .subscribe(new Action1<Long>() {
                        @Override
                        public void call(Long ignored) {
                            CouchbaseEvent event = generateCouchbaseEvent();
                            if (LOGGER.isTraceEnabled()) {
                                LOGGER.trace("Emitting Metric to EventBus: {}", event);
                            }
                            eventBus.publish(event);
                        }
                    });
        } else {
            subscription = null;
        }
    }

    protected abstract CouchbaseEvent generateCouchbaseEvent();

    @Override
    public MetricsCollectorConfig config() {
        return config;
    }

    @Override
    public boolean shutdown() {
        if (subscription == null) {
            return true;
        }

        if (!subscription.isUnsubscribed()) {
            subscription.unsubscribe();
        }

        return true;
    }
}
