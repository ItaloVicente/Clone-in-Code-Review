package com.couchbase.client.core.utils;

import io.netty.util.ReferenceCountUtil;
import rx.Observable;
import rx.Observer;
import rx.Scheduler;
import rx.Subscriber;
import rx.functions.Action0;
import rx.functions.Action1;
import rx.internal.operators.BufferUntilSubscriber;
import rx.observers.Subscribers;
import rx.schedulers.Schedulers;
import rx.subjects.Subject;
import rx.subscriptions.Subscriptions;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;

public final class UnicastAutoReleaseSubject<T> extends Subject<T, T> {

    private final State<T> state;
    private volatile Observable<Long> timeoutScheduler;

    private UnicastAutoReleaseSubject(State<T> state) {
        super(new OnSubscribeAction<T>(state));
        this.state = state;
        timeoutScheduler = Observable.empty();
    }

    private UnicastAutoReleaseSubject(final State<T> state, long noSubscriptionTimeout, TimeUnit timeUnit,
        Scheduler scheduler) {
        super(new OnSubscribeAction<T>(state));
        this.state = state;
        timeoutScheduler = Observable.interval(noSubscriptionTimeout, timeUnit, scheduler).take(1);
    }

    public static <T> UnicastAutoReleaseSubject<T> createWithoutNoSubscriptionTimeout(Action0 onUnsubscribe) {
        State<T> state = new State<T>(onUnsubscribe);
        return new UnicastAutoReleaseSubject<T>(state);
    }

    public static <T> UnicastAutoReleaseSubject<T> createWithoutNoSubscriptionTimeout() {
        return createWithoutNoSubscriptionTimeout(null);
    }

    public static <T> UnicastAutoReleaseSubject<T> create(long noSubscriptionTimeout, TimeUnit timeUnit) {
        return create(noSubscriptionTimeout, timeUnit, (Action0)null);
    }

    public static <T> UnicastAutoReleaseSubject<T> create(long noSubscriptionTimeout, TimeUnit timeUnit,
        Action0 onUnsubscribe) {
        return create(noSubscriptionTimeout, timeUnit, Schedulers.computation(), onUnsubscribe);
    }

    public static <T> UnicastAutoReleaseSubject<T> create(long noSubscriptionTimeout, TimeUnit timeUnit,
        Scheduler timeoutScheduler) {
        return create(noSubscriptionTimeout, timeUnit, timeoutScheduler, null);
    }

    public static <T> UnicastAutoReleaseSubject<T> create(long noSubscriptionTimeout, TimeUnit timeUnit,
        Scheduler timeoutScheduler, Action0 onUnsubscribe) {
        State<T> state = new State<T>(onUnsubscribe);
        return new UnicastAutoReleaseSubject<T>(state, noSubscriptionTimeout, timeUnit, timeoutScheduler);
    }

    public boolean disposeIfNotSubscribed() {
        if (state.casState(State.STATES.UNSUBSCRIBED, State.STATES.DISPOSED)) {
            state.bufferedObservable.subscribe(Subscribers.empty()); // Drain all items so that ByteBuf gets released.
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
            final BufferUntilSubscriber<T> bufferedSubject = BufferUntilSubscriber.create();
            bufferedObservable = bufferedSubject.lift(new AutoReleaseByteBufOperator<T>()); // Always auto-release
            bufferedObserver = bufferedSubject;
        }

        private enum STATES {
            UNSUBSCRIBED /*Initial*/, SUBSCRIBED /*Terminal state*/, DISPOSED/*Terminal state*/
        }

        private volatile int state = STATES.UNSUBSCRIBED.ordinal(); /*Values are the ordinals of STATES enum*/

        private final Observer<T> bufferedObserver;
        private final Observable<T> bufferedObservable;

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

                state.bufferedObservable.subscribe(subscriber);

            } else if(State.STATES.SUBSCRIBED.ordinal() == state.state) {
                subscriber.onError(new IllegalStateException("Content can only have one subscription. Use Observable.publish() if you want to multicast."));
            } else if(State.STATES.DISPOSED.ordinal() == state.state) {
                subscriber.onError(new IllegalStateException("Content stream is already disposed."));
            }
        }
    }

    private static class AutoReleaseByteBufOperator<I> implements Operator<I, I> {
        @Override
        public Subscriber<? super I> call(final Subscriber<? super I> subscriber) {
            return new Subscriber<I>() {
                @Override
                public void onCompleted() {
                    subscriber.onCompleted();
                }

                @Override
                public void onError(Throwable e) {
                    subscriber.onError(e);
                }

                @Override
                public void onNext(I t) {
                    try {
                        subscriber.onNext(t);
                    } finally {
                        ReferenceCountUtil.release(t);
                    }
                }
            };
        }
    }

    @Override
    public void onCompleted() {
        state.bufferedObserver.onCompleted();
    }

    @Override
    public void onError(Throwable e) {
        state.bufferedObserver.onError(e);
    }

    @Override
    public void onNext(T t) {
        ReferenceCountUtil.retain(t);
        state.bufferedObserver.onNext(t);

        if (state.casTimeoutScheduled() && state.state == State.STATES.UNSUBSCRIBED.ordinal()) {
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
        return state.state == State.STATES.SUBSCRIBED.ordinal();
    }
}
