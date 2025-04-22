package com.couchbase.client.core.utils;

import io.netty.buffer.ByteBuf;
import io.netty.util.ReferenceCountUtil;
import io.netty.util.ReferenceCounted;
import rx.Observable;
import rx.Scheduler;
import rx.Subscriber;
import rx.functions.Action0;
import rx.functions.Action1;
import rx.internal.operators.BufferUntilSubscriber;
import rx.schedulers.Schedulers;
import rx.subjects.Subject;
import rx.subscriptions.Subscriptions;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;

public final class UnicastContentSubject<T> extends Subject<T, T> {

    private final State<T> state;
    private volatile Observable<Long> timeoutScheduler;

    private UnicastContentSubject(State<T> state) {
        super(new OnSubscribeAction<T>(state));
        this.state = state;
        timeoutScheduler = Observable.empty(); // No timeout.
    }

    private UnicastContentSubject(final State<T> state, long noSubscriptionTimeout, TimeUnit timeUnit,
                                  Scheduler scheduler) {
        super(new OnSubscribeAction<T>(state));
        this.state = state;
        timeoutScheduler = Observable.interval(noSubscriptionTimeout, timeUnit, scheduler).take(1); // Started when content arrives.
    }

    public static <T> UnicastContentSubject<T> createWithoutNoSubscriptionTimeout(Action0 onUnsubscribe) {
        State<T> state = new State<T>(onUnsubscribe);
        return new UnicastContentSubject<T>(state);
    }

    public static <T> UnicastContentSubject<T> createWithoutNoSubscriptionTimeout() {
        return createWithoutNoSubscriptionTimeout(null);
    }

    public static <T> UnicastContentSubject<T> create(long noSubscriptionTimeout, TimeUnit timeUnit) {
        return create(noSubscriptionTimeout, timeUnit, (Action0)null);
    }

    public static <T> UnicastContentSubject<T> create(long noSubscriptionTimeout, TimeUnit timeUnit,
                                                      Action0 onUnsubscribe) {
        return create(noSubscriptionTimeout, timeUnit, Schedulers.computation(), onUnsubscribe);
    }

    public static <T> UnicastContentSubject<T> create(long noSubscriptionTimeout, TimeUnit timeUnit,
                                                      Scheduler timeoutScheduler) {
        return create(noSubscriptionTimeout, timeUnit, timeoutScheduler, null);
    }

    public static <T> UnicastContentSubject<T> create(long noSubscriptionTimeout, TimeUnit timeUnit,
                                                      Scheduler timeoutScheduler, Action0 onUnsubscribe) {
        State<T> state = new State<T>(onUnsubscribe);
        return new UnicastContentSubject<T>(state, noSubscriptionTimeout, timeUnit, timeoutScheduler);
    }

    public boolean disposeIfNotSubscribed() {
        if (state.casState(State.STATES.UNSUBSCRIBED, State.STATES.DISPOSED)) {
            state.bufferedSubject.subscribe(new Action1<T>() {
                @Override
                public void call(T t) {
                    if (t instanceof ReferenceCounted) {
                        ReferenceCountUtil.release(t, ((ReferenceCounted) t).refCnt());
                    }
                }
            }); // Drain all items so that ByteBuf gets released.

            if (null != state.onUnsubscribe) {
                state.onUnsubscribe.call(); // Since this is an inline/sync call, if this throws an error, it gets thrown to the caller.
            }
            return true;
        }
        return false;
    }

    public void updateTimeoutIfNotScheduled(long noSubscriptionTimeout, TimeUnit timeUnit) {
        if (0 == state.timeoutScheduled) {
            timeoutScheduler = Observable.interval(noSubscriptionTimeout, timeUnit).take(1);
        }
    }

    private static final class State<T> {

        private final Action0 onUnsubscribe;

        public State() {
            this(null);
        }

        public State(Action0 onUnsubscribe) {
            this.onUnsubscribe = onUnsubscribe;
        }

        private enum STATES {
            UNSUBSCRIBED /*Initial*/, SUBSCRIBED /*Terminal state*/, DISPOSED/*Terminal state*/
        }

        private volatile int state = STATES.UNSUBSCRIBED.ordinal(); /*Values are the ordinals of STATES enum*/

        private final BufferUntilSubscriber<T> bufferedSubject = BufferUntilSubscriber.create();

        @SuppressWarnings("unused")private volatile int timeoutScheduled; // Boolean

        @SuppressWarnings("rawtypes")
        private static final AtomicIntegerFieldUpdater<State> STATE_UPDATER
                = AtomicIntegerFieldUpdater.newUpdater(State.class, "state");

        @SuppressWarnings("rawtypes")
        private static final AtomicIntegerFieldUpdater<State> TIMEOUT_SCHEDULED_UPDATER
                = AtomicIntegerFieldUpdater.newUpdater(State.class, "timeoutScheduled");

        public boolean casState(STATES expected, STATES next) {
            return STATE_UPDATER.compareAndSet(this, expected.ordinal(), next.ordinal());
        }

        public boolean casTimeoutScheduled() {
            return TIMEOUT_SCHEDULED_UPDATER.compareAndSet(this, 0, 1);
        }
    }

    private static final class OnSubscribeAction<T> implements OnSubscribe<T> {

        private final State<T> state;

        public OnSubscribeAction(State<T> state) {
            this.state = state;
        }

        @Override
        public void call(final Subscriber<? super T> subscriber) {
            if (state.casState(State.STATES.UNSUBSCRIBED, State.STATES.SUBSCRIBED)) {

                subscriber.add(Subscriptions.create(new Action0() {
                    @Override
                    public void call() {
                        if (null != state.onUnsubscribe) {
                            state.onUnsubscribe.call();
                        }
                    }
                }));

                state.bufferedSubject.lift(new AutoReleaseByteBufOperator()).subscribe(subscriber);

            } else if(State.STATES.SUBSCRIBED.ordinal() == state.state) {
                subscriber.onError(new IllegalStateException("Content can only have one subscription. Use Observable.publish() if you want to multicast."));
            } else if(State.STATES.DISPOSED.ordinal() == state.state) {
                subscriber.onError(new IllegalStateException("Content stream is already disposed."));
            }
        }

        private class AutoReleaseByteBufOperator implements Operator<T, T> {
            @Override
            public Subscriber<? super T> call(final Subscriber<? super T> subscriber) {
                return new Subscriber<T>() {
                    @Override
                    public void onCompleted() {
                        subscriber.onCompleted();
                    }

                    @Override
                    public void onError(Throwable e) {
                        subscriber.onError(e);
                    }

                    @Override
                    public void onNext(T t) {
                        try {
                            subscriber.onNext(t);
                        } finally {
                            ReferenceCountUtil.release(t);
                        }
                    }
                };
            }
        }
    }

    @Override
    public void onCompleted() {
        state.bufferedSubject.onCompleted();
    }

    @Override
    public void onError(Throwable e) {
        state.bufferedSubject.onError(e);
    }

    @Override
    public void onNext(T t) {
        ReferenceCountUtil.retain(t); // Retain so that post-buffer, the ByteBuf does not get released. Release will be done after reading from the subject.
        state.bufferedSubject.onNext(t);

        if (state.casTimeoutScheduled() && state.state == 0) {// Schedule timeout once.
            timeoutScheduler.subscribe(new Action1<Long>() { // Schedule timeout after the first content arrives.
                @Override
                public void call(Long aLong) {
                    disposeIfNotSubscribed();
                }
            });
        }
    }

    @Override
    public boolean hasObservers() {
        return state.bufferedSubject.hasObservers();
    }
}
