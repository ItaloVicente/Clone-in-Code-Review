package com.couchbase.client.core.env;

import rx.Scheduler;
import rx.Subscription;
import rx.functions.Action0;
import rx.internal.schedulers.NewThreadWorker;
import rx.internal.schedulers.ScheduledAction;
import rx.internal.util.RxThreadFactory;
import rx.subscriptions.CompositeSubscription;
import rx.subscriptions.Subscriptions;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;

public class CoreScheduler extends Scheduler {

    private static final String THREAD_NAME_PREFIX = "cb-computations-";
    private static final RxThreadFactory THREAD_FACTORY = new RxThreadFactory(THREAD_NAME_PREFIX);

    final FixedSchedulerPool pool;

    public CoreScheduler(int poolSize) {
        pool = new FixedSchedulerPool(poolSize);
    }

    @Override
    public Worker createWorker() {
        return new EventLoopWorker(pool.getEventLoop());
    }

    static final class FixedSchedulerPool {
        final int size;

        final PoolWorker[] eventLoops;
        long n;

        FixedSchedulerPool(int poolSize) {
            this.size = poolSize;
            this.eventLoops = new PoolWorker[size];
            for (int i = 0; i < size; i++) {
                this.eventLoops[i] = new PoolWorker(THREAD_FACTORY);
            }
        }

        public PoolWorker getEventLoop() {
            return eventLoops[(int)(n++ % size)];
        }
    }

    private static class EventLoopWorker extends Scheduler.Worker {
        private final CompositeSubscription innerSubscription = new CompositeSubscription();
        private final PoolWorker poolWorker;

        EventLoopWorker(PoolWorker poolWorker) {
            this.poolWorker = poolWorker;

        }

        @Override
        public void unsubscribe() {
            innerSubscription.unsubscribe();
        }

        @Override
        public boolean isUnsubscribed() {
            return innerSubscription.isUnsubscribed();
        }

        @Override
        public Subscription schedule(Action0 action) {
            return schedule(action, 0, null);
        }
        @Override
        public Subscription schedule(Action0 action, long delayTime, TimeUnit unit) {
            if (innerSubscription.isUnsubscribed()) {
                return Subscriptions.empty();
            }

            ScheduledAction s = poolWorker.scheduleActual(action, delayTime, unit);
            innerSubscription.add(s);
            s.addParent(innerSubscription);
            return s;
        }
    }

    private static final class PoolWorker extends NewThreadWorker {
        PoolWorker(ThreadFactory threadFactory) {
            super(threadFactory);
        }
    }

}
