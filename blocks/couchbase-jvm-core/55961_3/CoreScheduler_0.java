package com.couchbase.client.core;

import com.couchbase.client.core.ClusterFacade;
import com.couchbase.client.core.CouchbaseCore;
import com.couchbase.client.core.env.CoreEnvironment;
import com.couchbase.client.core.env.DefaultCoreEnvironment;
import com.couchbase.client.core.logging.CouchbaseLogger;
import com.couchbase.client.core.logging.CouchbaseLoggerFactory;
import com.couchbase.client.core.message.cluster.DisconnectRequest;
import com.couchbase.client.core.message.cluster.OpenBucketRequest;
import com.couchbase.client.core.message.cluster.OpenBucketResponse;
import com.couchbase.client.core.message.cluster.SeedNodesRequest;
import com.couchbase.client.core.message.cluster.SeedNodesResponse;
import com.couchbase.client.core.util.TestProperties;
import org.junit.Test;
import rx.Observable;
import rx.functions.Func1;

import java.lang.management.ManagementFactory;
import java.lang.management.ThreadInfo;
import java.lang.management.ThreadMXBean;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class ThreadCleanupTest {

    private static final CouchbaseLogger LOGGER = CouchbaseLoggerFactory.getInstance(ThreadCleanupTest.class);

    private static final String seedNode = TestProperties.seedNode();
    private static final String bucket = TestProperties.bucket();
    private static final String password = TestProperties.password();

    private CoreEnvironment env;

    private ClusterFacade cluster;

    public void connect() {
        env = DefaultCoreEnvironment
                .builder()
                .dcpEnabled(true)
                .mutationTokensEnabled(true)
                .build();
        cluster = new CouchbaseCore(env);
        cluster.<SeedNodesResponse>send(new SeedNodesRequest(seedNode)).flatMap(
                new Func1<SeedNodesResponse, Observable<OpenBucketResponse>>() {
                    @Override
                    public Observable<OpenBucketResponse> call(SeedNodesResponse response) {
                        return cluster.send(new OpenBucketRequest(bucket, password));
                    }
                }
        ).toBlocking().single();
    }

    public void disconnect() throws InterruptedException {
        cluster.send(new DisconnectRequest()).toBlocking().first();
    }

    @Test
    public void testSdkNettyRxJavaThreadsShutdownProperly() throws InterruptedException {
        ThreadMXBean mx = ManagementFactory.getThreadMXBean();
        LOGGER.info("Threads at start");
        Set<String> ignore = dump(threads(mx));

        connect();

        LOGGER.info("Threads before shutdown:");
        Set<String> beforeShutdown = dump(threads(mx, ignore, false));

        LOGGER.info("");
        LOGGER.info("Shutting Down Couchbase Cluster");
        disconnect();
        LOGGER.info("Shutting Down Couchbase Env");
        env.shutdown();
        LOGGER.info("Shutting Down RxJava should be implemented here");
        LOGGER.info("");
        LOGGER.info("Threads after shutdown:");
        Set<String> afterShutdown = dump(threads(mx, ignore, true));
        boolean hasShutdown = Collections.disjoint(afterShutdown, beforeShutdown);
        if (hasShutdown)
            LOGGER.info("All relevant threads properly cleaned up after shutdown");

        assertTrue("Some threads created by the SDK remained after shutdown", hasShutdown);
    }

    private Set<String> dump(Set<String> threads) {
        for (String thread : threads) {
            LOGGER.info(thread);
        }
        return threads;
    }

    private Set<String> threads(ThreadMXBean mx, Set<String> ignore, boolean shouldIgnoreRxJavaThreads) {
        Set<String> all = threads(mx);
        all.removeAll(ignore);
        if (shouldIgnoreRxJavaThreads) {
            for (Iterator<String> iterator = all.iterator(); iterator.hasNext(); ) {
                String next = iterator.next();
                if (next.startsWith("Rx"))
                    iterator.remove();
            }
        }
        return all;
    }

    private Set<String> threads(ThreadMXBean mx) {
        ThreadInfo[] dump = mx.getThreadInfo(mx.getAllThreadIds());
        Set<String> names = new HashSet<String>(dump.length);
        for (ThreadInfo threadInfo : dump) {
            if (threadInfo == null || threadInfo.getThreadName() == null) {
                continue;
            }

            names.add(threadInfo.getThreadName());
        }
        return names;
    }
}
