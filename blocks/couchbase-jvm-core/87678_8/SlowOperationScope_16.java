
package com.couchbase.client.core.tracing;

import com.couchbase.client.core.logging.CouchbaseLogger;
import com.couchbase.client.core.logging.CouchbaseLoggerFactory;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.netty.util.internal.shaded.org.jctools.queues.MpscUnboundedArrayQueue;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.SortedMap;
import java.util.SortedSet;
import java.util.TreeSet;

public class SlowOperationReporter {

    private static final ObjectMapper JACKSON = new ObjectMapper();

    private static final CouchbaseLogger LOGGER = CouchbaseLoggerFactory.getInstance(SlowOperationReporter.class);

    private final Queue<SlowOperationSpan> spansToTrack;

    public SlowOperationReporter() {
        spansToTrack = new MpscUnboundedArrayQueue<SlowOperationSpan>(1024);

        Thread thread = new Thread(new Worker());
        thread.setDaemon(true);
        thread.start();
    }

    public void report(final SlowOperationSpan span) {
        if (span.getTag("couchbase.service") != null) {
            if (!spansToTrack.offer(span)) {
                LOGGER.warn("Could not enqueue span {} for slowlog reporting, discarding.", span);
            }
        }
    }

    class Worker implements Runnable {

        private final long sleepTimeMs = 1000;
        private final long topSpans = 10;
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
                    Thread.sleep(sleepTimeMs);
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
                        while (kvSet.size() > topSpans) {
                            kvSet.remove(kvSet.first());
                        }
                    } else if (service.equals("n1ql")) {
                        n1qlSet.add(span);
                        totalInInterval++;
                        while (n1qlSet.size() > topSpans) {
                            n1qlSet.remove(n1qlSet.first());
                        }
                    } else if (service.equals("view")) {
                        viewSet.add(span);
                        totalInInterval++;
                        while (viewSet.size() > topSpans) {
                            viewSet.remove(viewSet.first());
                        }
                    } else if (service.equals("fts")) {
                        ftsSet.add(span);
                        totalInInterval++;
                        while (ftsSet.size() > topSpans) {
                            ftsSet.remove(ftsSet.first());
                        }
                    } else if (service.equals("analytics")) {
                        analyticsSet.add(span);
                        totalInInterval++;
                        while (analyticsSet.size() > topSpans) {
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

}
