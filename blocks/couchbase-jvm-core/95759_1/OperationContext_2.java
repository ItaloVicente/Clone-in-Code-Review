
import com.couchbase.client.core.logging.CouchbaseLogger;
import com.couchbase.client.core.logging.CouchbaseLoggerFactory;
import com.couchbase.client.core.tracing.OperationContext;
import io.netty.util.internal.shaded.org.jctools.queues.MpscUnboundedArrayQueue;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import static com.couchbase.client.core.utils.DefaultObjectMapper.prettyWriter;
import static com.couchbase.client.core.utils.DefaultObjectMapper.writer;

public class DefaultZombieResponseReporter {

    private static final CouchbaseLogger LOGGER = CouchbaseLoggerFactory.getInstance(DefaultZombieResponseReporter.class);

    private static final AtomicInteger REPORTER_ID = new AtomicInteger();

    private static final long MIN_LOG_INTERVAL = TimeUnit.SECONDS.toNanos(1);

    public static final String SERVICE_KV = "kv";
    public static final String SERVICE_N1QL = "n1ql";
    public static final String SERVICE_FTS = "search";
    public static final String SERVICE_VIEW = "view";
    public static final String SERVICE_ANALYTICS = "analytics";

    private final Queue<OperationContext> queue;

    private final long logIntervalNanos;
    private final int sampleSize;
    private final boolean pretty;

    private final Thread worker;

    private volatile boolean running;

    public static DefaultZombieResponseReporter.Builder builder() {
        return new DefaultZombieResponseReporter.Builder();
    }

    public static DefaultZombieResponseReporter disabled() {
        return builder().logInterval(0, TimeUnit.SECONDS).build();
    }

    public static DefaultZombieResponseReporter create() {
        return builder().build();
    }

    public DefaultZombieResponseReporter(final Builder builder) {
        logIntervalNanos = builder.logIntervalUnit.toNanos(builder.logInterval);
        sampleSize = builder.sampleSize;
        if (logIntervalNanos > 0 && logIntervalNanos < minLogInterval()) {
            throw new IllegalArgumentException("The log interval needs to be either 0 or greater than "
                    + MIN_LOG_INTERVAL + " micros");
        }

        queue = new MpscUnboundedArrayQueue<OperationContext>(builder.spanQueueSize);
        pretty = builder.pretty;
        running = true;

        if (logIntervalNanos > 0) {
            worker = new Thread(new Worker());
            worker.setDaemon(true);
            worker.start();
        } else {
            worker = null;
            LOGGER.debug("ZombieResponseLogReporter disabled via config.");
        }
    }

    long minLogInterval() {
        return MIN_LOG_INTERVAL;
    }

    public void Report(OperationContext context) {
        if (!queue.offer(context)) {
            LOGGER.debug("Could not enqueue OperationContext {} for zombie reporting, discarding.", context);
        }
    }

    public void shutdown() {
        running = false;
        if (worker != null) {
            worker.interrupt();
        }
    }

    public static class Builder {

        private static final long DEFAULT_LOG_INTERVAL = 10;
        private static final TimeUnit DEFAULT_LOG_INTERVAL_UNIT = TimeUnit.SECONDS;
        private static final int DEFAULT_SPAN_QUEUE_SIZE = 1024;
        private static final int DEFAULT_SAMPLE_SIZE = 10;
        private static final boolean DEFAULT_PRETTY = false;

        private long logInterval = DEFAULT_LOG_INTERVAL;
        private TimeUnit logIntervalUnit = DEFAULT_LOG_INTERVAL_UNIT;
        private int spanQueueSize = DEFAULT_SPAN_QUEUE_SIZE;
        private int sampleSize = DEFAULT_SAMPLE_SIZE;
        private boolean pretty = DEFAULT_PRETTY;

        public DefaultZombieResponseReporter build() {
            return new DefaultZombieResponseReporter(this);
        }

        public Builder logInterval(final long interval, final TimeUnit unit) {
            this.logInterval = interval;
            this.logIntervalUnit = unit;
            return this;
        }

        public Builder sampleSize(final int sampleSize) {
            this.sampleSize = sampleSize;
            return this;
        }

        public Builder pretty(final boolean pretty) {
            this.pretty = pretty;
            return this;
        }
    }

    class Worker implements Runnable {

        private final long workerSleepMs = Long.parseLong(
            System.getProperty("com.couchbase.zombieResponseLogReporterSleep", "100")
        );

        private final SortedSet<OperationContext> kvZombieSet = new TreeSet<OperationContext>();
        private final SortedSet<OperationContext> n1qlZombieSet = new TreeSet<OperationContext>();
        private final SortedSet<OperationContext> viewZombieSet = new TreeSet<OperationContext>();
        private final SortedSet<OperationContext> ftsZombieSet = new TreeSet<OperationContext>();
        private final SortedSet<OperationContext> analyticsZombieSet = new TreeSet<OperationContext>();

        private long lastZombieLog;
        private boolean hasZombieWritten;

        @Override
        public void run() {
            Thread.currentThread().setName("cb-zombie-" + REPORTER_ID.incrementAndGet());
            while (running) {
                try {
                    handlerZombieQueue();
                    Thread.sleep(workerSleepMs);
                } catch (final InterruptedException ex) {
                    if (!running) {
                        return;
                    } else {
                        Thread.currentThread().interrupt();
                    }
                } catch (final Exception ex) {
                    LOGGER.warn("Got exception on zombie response log reporter, ignoring.", ex);
                }
            }
        }

        private void handlerZombieQueue() {
            long now = System.nanoTime();
            if ((now - lastZombieLog + logIntervalNanos) > 0) {
                prepareAndLogZombies();
                lastZombieLog = now;
            }

            while (true) {
                OperationContext context = queue.poll();
                if (context == null) {
                    return;
                }
                String service = context.serviceType;
                if (SERVICE_KV.equals(service)) {
                    updateSet(kvZombieSet, context);
                } else if (SERVICE_N1QL.equals(service)) {
                    updateSet(n1qlZombieSet, context);
                } else if (SERVICE_VIEW.equals(service)) {
                    updateSet(viewZombieSet, context);
                } else if (SERVICE_FTS.equals(service)) {
                    updateSet(ftsZombieSet, context);
                } else if (SERVICE_ANALYTICS.equals(service)) {
                    updateSet(analyticsZombieSet, context);
                } else {
                    LOGGER.warn("Unknown service in span {}", service);
                }
            }
        }

        private void updateSet(final SortedSet<OperationContext> set, final OperationContext span) {
            set.add(span);
            while(set.size() > sampleSize) {
                set.remove(set.first());
            }
            hasZombieWritten = true;
        }

        private void prepareAndLogZombies() {
            if (!hasZombieWritten) {
                return;
            }
            hasZombieWritten = false;

            List<Map<String, Object>> output = new ArrayList<Map<String, Object>>();

            if (!kvZombieSet.isEmpty()) {
                output.add(convertThresholdSet(kvZombieSet, SERVICE_KV));
                kvZombieSet.clear();
            }
            if (!n1qlZombieSet.isEmpty()) {
                output.add(convertThresholdSet(n1qlZombieSet, SERVICE_N1QL));
                n1qlZombieSet.clear();
            }
            if (!viewZombieSet.isEmpty()) {
                output.add(convertThresholdSet(viewZombieSet, SERVICE_VIEW));
                viewZombieSet.clear();
            }
            if (!ftsZombieSet.isEmpty()) {
                output.add(convertThresholdSet(ftsZombieSet, SERVICE_FTS));
                ftsZombieSet.clear();
            }
            if (!analyticsZombieSet.isEmpty()) {
                output.add(convertThresholdSet(analyticsZombieSet, SERVICE_ANALYTICS));
                analyticsZombieSet.clear();
            }

            logZombies(output);
        }

        private Map<String, Object> convertThresholdSet(SortedSet<OperationContext> set, String serviceType) {
            Map<String, Object> output = new HashMap<String, Object>();
            List<Map<String, Object>> top = new ArrayList<Map<String, Object>>();
            for (OperationContext context : set) {
                HashMap<String, Object> entry = context.ToHashMap();
                top.add(entry);
            }
            output.put("service", serviceType);
            output.put("count", set.size());
            output.put("top", top);
            return output;
        }
    }

    void logZombies(final List<Map<String, Object>> toLog) {
        try {
            String result = pretty
                    ? prettyWriter().writeValueAsString(toLog)
                    : writer().writeValueAsString(toLog);
            LOGGER.warn("Zombie responses observed: {}", result);
        } catch (Exception ex) {
            LOGGER.warn("Could not write zombie log.", ex);
        }
    }
}
