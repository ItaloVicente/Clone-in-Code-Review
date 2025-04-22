
package com.couchbase.client.core.tracing;

import com.couchbase.client.core.logging.CouchbaseLogger;
import com.couchbase.client.core.logging.CouchbaseLoggerFactory;
import io.netty.util.internal.shaded.org.jctools.queues.MpscUnboundedArrayQueue;
import io.opentracing.Tracer;

import java.util.Queue;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class ThresholdLogReporter {

    private static final CouchbaseLogger LOGGER =
        CouchbaseLoggerFactory.getInstance(ThresholdLogReporter.class);

    private static final AtomicInteger REPORTER_ID = new AtomicInteger();

    private static final long MIN_LOG_INTERVAL = TimeUnit.SECONDS.toNanos(1);

    private final Queue<ThresholdLogSpan> overThresholdQueue;
    private final Queue<ThresholdLogSpan> zombieQueue;

    private final long kvThreshold;
    private final long n1qlThreshold;
    private final long viewThreshold;
    private final long ftsThreshold;
    private final long analyticsThreshold;

    public static ThresholdLogReporter.Builder builder() {
        return new Builder();
    }

    public static ThresholdLogReporter disabled() {
        return builder().logInterval(0, TimeUnit.SECONDS).build();
    }

    public static ThresholdLogReporter create() {
        return builder().build();
    }

    private ThresholdLogReporter(final Builder builder) {
        long logIntervalNanos = builder.logIntervalUnit.toNanos(builder.logInterval);
        if (logIntervalNanos > 0 && logIntervalNanos < MIN_LOG_INTERVAL) {
            throw new IllegalArgumentException("The log interval needs to be either 0 or greater than "
                + MIN_LOG_INTERVAL + " micros");
        }

        overThresholdQueue = new MpscUnboundedArrayQueue<ThresholdLogSpan>(builder.spanQueueSize);
        zombieQueue = new MpscUnboundedArrayQueue<ThresholdLogSpan>(builder.spanQueueSize);
        kvThreshold = builder.kvThreshold;
        analyticsThreshold = builder.analyticsThreshold;
        ftsThreshold = builder.ftsThreshold;
        viewThreshold = builder.viewThreshold;
        n1qlThreshold = builder.n1qlThreshold;

        if (logIntervalNanos > 0) {
            Thread worker = new Thread(
                new Worker(logIntervalNanos, overThresholdQueue, zombieQueue)
            );
            worker.setDaemon(true);
            worker.run();
        } else {
            LOGGER.debug("ThresholdLogReporter disabled via config.");
        }
    }

    public void report(final ThresholdLogSpan span) {
        if (isZombie(span)) {
            if (!zombieQueue.offer(span)) {
                LOGGER.debug("Could not enqueue span {} for zombie reporting, discarding.", span);
            }
        } else if (isOverThreshold(span)) {
            if (!overThresholdQueue.offer(span)) {
                LOGGER.debug("Could not enqueue span {} for over threshold reporting, discarding.", span);
            }
        }
    }

    private boolean isOverThreshold(final ThresholdLogSpan span) {
        String service = (String) span.tag("couchbase.service");
        if (service.equals("kv")) {
            return span.durationMicros() >= kvThreshold;
        } else if (service.equals("n1ql")) {
            return span.durationMicros() >= n1qlThreshold;
        } else if (service.equals("view")) {
            return span.durationMicros() >= viewThreshold;
        } else if (service.equals("fts")) {
            return span.durationMicros() >= ftsThreshold;
        } else if (service.equals("analytics")) {
            return span.durationMicros() >= analyticsThreshold;
        } else {
            LOGGER.warn("Unknown service in span {}", span);
            return false;
        }
    }

    private boolean isZombie(final ThresholdLogSpan span) {
        return span.getBaggageItem("couchbase.zombie").equals("true");
    }

    public static class Builder {

        private static final long DEFAULT_LOG_INTERVAL = 10;
        private static final TimeUnit DEFAULT_LOG_INTERVAL_UNIT = TimeUnit.SECONDS;
        private static final int DEFAULT_SPAN_QUEUE_SIZE = 1024;
        private static final long DEFAULT_KV_THRESHOLD = TimeUnit.MILLISECONDS.toMicros(500);
        private static final long DEFAULT_N1QL_THRESHOLD = TimeUnit.SECONDS.toMicros(1);
        private static final long DEFAULT_VIEW_THRESHOLD = TimeUnit.SECONDS.toMicros(1);
        private static final long DEFAULT_FTS_THRESHOLD = TimeUnit.SECONDS.toMicros(1);
        private static final long DEFAULT_ANALYTICS_THRESHOLD = TimeUnit.SECONDS.toMicros(1);

        private long logInterval = DEFAULT_LOG_INTERVAL;
        private TimeUnit logIntervalUnit = DEFAULT_LOG_INTERVAL_UNIT;
        private int spanQueueSize = DEFAULT_SPAN_QUEUE_SIZE;

        private long kvThreshold = DEFAULT_KV_THRESHOLD;
        private long n1qlThreshold = DEFAULT_N1QL_THRESHOLD;
        private long viewThreshold = DEFAULT_VIEW_THRESHOLD;
        private long ftsThreshold = DEFAULT_FTS_THRESHOLD;
        private long analyticsThreshold = DEFAULT_ANALYTICS_THRESHOLD;

        public ThresholdLogReporter build() {
            return new ThresholdLogReporter(this);
        }

        public Builder logInterval(final long interval, final TimeUnit unit) {
            this.logInterval = interval;
            this.logIntervalUnit = unit;
            return this;
        }

        public Builder spanQueueSize(final int spanQueueSize) {
            this.spanQueueSize = spanQueueSize;
            return this;
        }

        public Builder kvThreshold(final long kvThreshold) {
            this.kvThreshold = kvThreshold;
            return this;
        }

        public Builder n1qlThreshold(final long n1qlThreshold) {
            this.n1qlThreshold = n1qlThreshold;
            return this;
        }

        public Builder viewThreshold(final long viewThreshold) {
            this.viewThreshold = viewThreshold;
            return this;
        }

        public Builder ftsThreshold(final long ftsThreshold) {
            this.ftsThreshold = ftsThreshold;
            return this;
        }

        public Builder analyticsThreshold(final long analyticsThreshold) {
            this.analyticsThreshold = analyticsThreshold;
            return this;
        }

    }

    class Worker implements Runnable {

        private final long workerSleepMs = Long.parseLong(
            System.getProperty("com.couchbase.thresholdLogReporterSleep", "100")
        );

        private final long logIntervalNanos;
        private final Queue<ThresholdLogSpan> overThresholdQueue;
        private final Queue<ThresholdLogSpan> zombieQueue;

        private long lastThresholdLog;

        private final SortedSet<ThresholdLogSpan> kvThresholdSet;
        private final SortedSet<ThresholdLogSpan> n1qlThresholdSet;
        private final SortedSet<ThresholdLogSpan> viewThresholdSet;
        private final SortedSet<ThresholdLogSpan> ftsThresholdSet;
        private final SortedSet<ThresholdLogSpan> analyticsThresholdSet;

        private long lastZombieLog;

        private final SortedSet<ThresholdLogSpan> kvZombieSet;
        private final SortedSet<ThresholdLogSpan> n1qlZombieSet;
        private final SortedSet<ThresholdLogSpan> viewZombieSet;
        private final SortedSet<ThresholdLogSpan> ftsZombieSet;
        private final SortedSet<ThresholdLogSpan> analyticsZombieSet;

        Worker(final long logIntervalNanos,
            final Queue<ThresholdLogSpan> overThresholdQueue,
            final Queue<ThresholdLogSpan> zombieQueue) {
            this.logIntervalNanos = logIntervalNanos;
            this.overThresholdQueue = overThresholdQueue;
            this.zombieQueue = zombieQueue;

            kvThresholdSet = new TreeSet<ThresholdLogSpan>();
            n1qlThresholdSet = new TreeSet<ThresholdLogSpan>();
            viewThresholdSet = new TreeSet<ThresholdLogSpan>();
            ftsThresholdSet = new TreeSet<ThresholdLogSpan>();
            analyticsThresholdSet = new TreeSet<ThresholdLogSpan>();

            kvZombieSet = new TreeSet<ThresholdLogSpan>();
            n1qlZombieSet = new TreeSet<ThresholdLogSpan>();
            viewZombieSet = new TreeSet<ThresholdLogSpan>();
            ftsZombieSet = new TreeSet<ThresholdLogSpan>();
            analyticsZombieSet = new TreeSet<ThresholdLogSpan>();
        }

        @Override
        public void run() {
            Thread.currentThread().setName("cb-tracing-" + REPORTER_ID.incrementAndGet());
            while (true) {
                try {
                    handleOverThresholdQueue();
                    handlerZombieQueue();
                    Thread.sleep(workerSleepMs);
                } catch (final Exception ex) {
                    LOGGER.warn("Got exception on slow operation reporter, ignoring.", ex);
                }
            }
        }

        private void handleOverThresholdQueue() {
            long now = System.nanoTime();
            if ((now - lastThresholdLog + logIntervalNanos) > 0) {
                lastThresholdLog = now;
            }

        }

        private void handlerZombieQueue() {
            long now = System.nanoTime();
            if ((now - lastZombieLog + logIntervalNanos) > 0) {
                lastZombieLog = now;
            }

        }
    }

}
