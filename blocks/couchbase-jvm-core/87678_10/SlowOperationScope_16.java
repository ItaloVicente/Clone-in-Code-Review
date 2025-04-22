
package com.couchbase.client.core.tracing;

import com.couchbase.client.core.logging.CouchbaseLogger;
import com.couchbase.client.core.logging.CouchbaseLoggerFactory;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.netty.util.internal.shaded.org.jctools.queues.MpscUnboundedArrayQueue;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.concurrent.TimeUnit;

public class SlowOperationReporter {

    private static final long DEFAULT_LOG_INTERVAL = TimeUnit.SECONDS.toMicros(1);
    private static final long DEFAULT_SAMPLE_SIZE = 10;
    private static final long DEFAULT_KV_THRESHOLD = TimeUnit.SECONDS.toMicros(1);
    private static final long DEFAULT_N1QL_THRESHOLD = TimeUnit.SECONDS.toMicros(5);
    private static final long DEFAULT_VIEW_THRESHOLD = TimeUnit.SECONDS.toMicros(5);
    private static final long DEFAULT_FTS_THRESHOLD = TimeUnit.SECONDS.toMicros(5);
    private static final long DEFAULT_ANALYTICS_THRESHOLD = TimeUnit.SECONDS.toMicros(5);

    private static final ObjectMapper JACKSON = new ObjectMapper();

    private static final CouchbaseLogger LOGGER = CouchbaseLoggerFactory.getInstance(SlowOperationReporter.class);

    private final Queue<SlowOperationSpan> spansToTrack;

    private final long kvThreshold;
    private final long n1qlThreshold;
    private final long viewThreshold;
    private final long ftsThreshold;
    private final long analyticsThreshold;

    private final long logInterval;
    private final long sampleSize;

    public static Builder builder() {
        return new Builder();
    }

    public static SlowOperationReporter createWithDefaults() {
        return builder().build();
    }

    public static SlowOperationReporter createWithNoThresholds(long sampleSize, long logInterval) {
        return builder()
            .analyticsThreshold(0)
            .ftsThreshold(0)
            .kvThreshold(0)
            .n1qlThreshold(0)
            .viewThreshold(0)
            .sampleSize(sampleSize)
            .logInterval(logInterval)
            .build();
    }

    SlowOperationReporter(final Builder builder) {
        spansToTrack = new MpscUnboundedArrayQueue<SlowOperationSpan>(1024);
        logInterval = TimeUnit.MICROSECONDS.toMillis(builder.logInterval());
        kvThreshold = builder.kvThreshold();
        n1qlThreshold = builder.n1qlThreshold();
        viewThreshold = builder.viewThreshold();
        ftsThreshold = builder.ftsThreshold();
        analyticsThreshold = builder.analyticsThreshold();
        sampleSize = builder.sampleSize();

        Thread thread = new Thread(new Worker());
        thread.setDaemon(true);
        thread.start();
    }

    public void report(final SlowOperationSpan span) {
        if (span.getTag("couchbase.service") != null && spanOverThreshold(span)) {
            if (!spansToTrack.offer(span)) {
                LOGGER.warn("Could not enqueue span {} for slowlog reporting, discarding.", span);
            }
        }
    }

    private boolean spanOverThreshold(final SlowOperationSpan span) {
        String service = (String) span.getTag("couchbase.service");
        if (service.equals("kv")) {
            return span.duration() >= kvThreshold;
        } else if (service.equals("n1ql")) {
            return span.duration() >= n1qlThreshold;
        } else if (service.equals("view")) {
            return span.duration() >= viewThreshold;
        } else if (service.equals("fts")) {
            return span.duration() >= ftsThreshold;
        } else if (service.equals("analytics")) {
            return span.duration() >= analyticsThreshold;
        } else {
            LOGGER.warn("Unknown service in span {}", service);
            return false;
        }
    }

    class Worker implements Runnable {
        private long totalInInterval = 0;
        private final SortedSet<SlowOperationSpan> kvSet;
        private final SortedSet<SlowOperationSpan> n1qlSet;
        private final SortedSet<SlowOperationSpan> viewSet;
        private final SortedSet<SlowOperationSpan> ftsSet;
        private final SortedSet<SlowOperationSpan> analyticsSet;

        Worker() {
            kvSet = new TreeSet<SlowOperationSpan>();
            n1qlSet = new TreeSet<SlowOperationSpan>();
            viewSet = new TreeSet<SlowOperationSpan>();
            ftsSet = new TreeSet<SlowOperationSpan>();
            analyticsSet = new TreeSet<SlowOperationSpan>();
        }

        @Override
        public void run() {
            while(true) {
                try {
                    grabNewSpansFromQueue();
                    logSlowOps();
                    Thread.sleep(logInterval);
                } catch (Exception ex) {
                    LOGGER.warn("Got exception on slow operation reporter, ignoring.", ex);
                }
            }
        }

        private void grabNewSpansFromQueue() {
            while(true) {
                SlowOperationSpan span = spansToTrack.poll();
                if (span == null) {
                    return;
                } else {
                    String service = (String) span.getTag("couchbase.service");
                    if (service.equals("kv")) {
                        kvSet.add(span);
                        totalInInterval++;
                        while (kvSet.size() > sampleSize) {
                            kvSet.remove(kvSet.first());
                        }
                    } else if (service.equals("n1ql")) {
                        n1qlSet.add(span);
                        totalInInterval++;
                        while (n1qlSet.size() > sampleSize) {
                            n1qlSet.remove(n1qlSet.first());
                        }
                    } else if (service.equals("view")) {
                        viewSet.add(span);
                        totalInInterval++;
                        while (viewSet.size() > sampleSize) {
                            viewSet.remove(viewSet.first());
                        }
                    } else if (service.equals("fts")) {
                        ftsSet.add(span);
                        totalInInterval++;
                        while (ftsSet.size() > sampleSize) {
                            ftsSet.remove(ftsSet.first());
                        }
                    } else if (service.equals("analytics")) {
                        analyticsSet.add(span);
                        totalInInterval++;
                        while (analyticsSet.size() > sampleSize) {
                            analyticsSet.remove(analyticsSet.first());
                        }
                    } else {
                        LOGGER.warn("Unknown service in span {}", service);
                    }
                }
            }
        }

        private void logSlowOps() throws Exception {
            if (totalInInterval == 0) {
                return;
            }

            long total = totalInInterval;
            totalInInterval = 0;

            Map<String, Object> result = new HashMap<String, Object>();
            Map<String, List<String>> top = new HashMap<String, List<String>>();

            buildSpanMap(top);

            result.put("count", total);
            result.put("top", top);

            LOGGER.warn("Zombie responses received: {}", JACKSON.writeValueAsString(result));
        }

        private void buildSpanMap(final Map<String, List<String>> input) {
            Iterator<SlowOperationSpan> kvIter = kvSet.iterator();
            while(kvIter.hasNext()) {
                SlowOperationSpan span = kvIter.next();
                String remote = (String) span.getTag("peer.hostname");
                if (remote == null) {
                    remote = "<unknown>";
                }
                String port = Integer.toString((Integer) span.getTag("peer.port"));
                if (port.isEmpty()) {
                    port = "<unknown>";
                }
                remote = remote + ":" + port;
                List<String> tops = input.get(remote);
                if (tops == null) {
                    tops = new ArrayList<String>();
                    input.put(remote, tops);
                }
                tops.add(span.getOperationName() + ":" + span.duration());
            }
            kvSet.clear();
        }
    }

    public static class Builder {

        private long logInterval = DEFAULT_LOG_INTERVAL;
        private long sampleSize = DEFAULT_SAMPLE_SIZE;
        private long kvThreshold = DEFAULT_KV_THRESHOLD;
        private long n1qlThreshold = DEFAULT_N1QL_THRESHOLD;
        private long viewThreshold = DEFAULT_VIEW_THRESHOLD;
        private long ftsThreshold = DEFAULT_FTS_THRESHOLD;
        private long analyticsThreshold = DEFAULT_ANALYTICS_THRESHOLD;

        Builder() {}

        public long logInterval() {
            return logInterval;
        }

        public Builder logInterval(long logInterval) {
            this.logInterval = logInterval;
            return this;
        }

        public long sampleSize() {
            return sampleSize;
        }

        public Builder sampleSize(long sampleSize) {
            this.sampleSize = sampleSize;
            return this;
        }

        public long kvThreshold() {
            return kvThreshold;
        }

        public Builder kvThreshold(long kvThreshold) {
            this.kvThreshold = kvThreshold;
            return this;
        }

        public long n1qlThreshold() {
            return n1qlThreshold;
        }

        public Builder n1qlThreshold(long n1qlThreshold) {
            this.n1qlThreshold = n1qlThreshold;
            return this;
        }

        public long viewThreshold() {
            return viewThreshold;
        }

        public Builder viewThreshold(long viewThreshold) {
            this.viewThreshold = viewThreshold;
            return this;
        }

        public long ftsThreshold() {
            return ftsThreshold;
        }

        public Builder ftsThreshold(long ftsThreshold) {
            this.ftsThreshold = ftsThreshold;
            return this;
        }

        public long analyticsThreshold() {
            return analyticsThreshold;
        }

        public Builder analyticsThreshold(long analyticsThreshold) {
            this.analyticsThreshold = analyticsThreshold;
            return this;
        }

        public SlowOperationReporter build() {
            return new SlowOperationReporter(this);
        }
    }

}
