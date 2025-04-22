package com.couchbase.client.core.utils;

import io.netty.util.ReferenceCountUtil;
import rx.Observable;
import rx.Scheduler;
import rx.Subscriber;
import rx.Subscription;
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
            state.bufferedSubject.lift(new AutoReleaseByteBufOperator<T>()).subscribe(Subscribers.empty()); // Drain all items so that ByteBuf gets released.
            return true;
        }
        return false;
    }

    private static final class State<T> {

        private enum STATES {
            UNSUBSCRIBED /*Initial*/, SUBSCRIBED /*Terminal state*/, DISPOSED/*Terminal state*/
        }

        private static final AtomicIntegerFieldUpdater<State> STATE_UPDATER
                = AtomicIntegerFieldUpdater.newUpdater(State.class, "state");
        private static final AtomicIntegerFieldUpdater<State> TIMEOUT_SCHEDULED_UPDATER
                = AtomicIntegerFieldUpdater.newUpdater(State.class, "timeoutScheduled");

        private final Action0 onUnsubscribe;
        private final Subject<T, T> bufferedSubject;

        private volatile Subscription timeoutSubscription;
        @SuppressWarnings("unused") private volatile int timeoutScheduled; // Boolean
        private volatile int state = STATES.UNSUBSCRIBED.ordinal(); /*Values are the ordinals of STATES enum*/

        public State(Action0 onUnsubscribe) {
            this.onUnsubscribe = onUnsubscribe;
            bufferedSubject = BufferUntilSubscriber.create();
        }

        public boolean casState(STATES expected, STATES next) {
            return STATE_UPDATER.compareAndSet(this, expected.ordinal(), next.ordinal());
        }

        public boolean casTimeoutScheduled() {
            return TIMEOUT_SCHEDULED_UPDATER.compareAndSet(this, 0, 1);
        }

        public void setTimeoutSubscription(Subscription subscription) {
            timeoutSubscription = subscription;
        }

        public void unsubscribeTimeoutSubscription() {
            if (timeoutSubscription != null) {
                timeoutSubscription.unsubscribe();
            }
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

                state.bufferedSubject.subscribe(subscriber);
                state.unsubscribeTimeoutSubscription();

            } else if(State.STATES.SUBSCRIBED.ordinal() == state.state) {
                subscriber.onError(new IllegalStateException("This Observable can only have one subscription. "
                    + "Use Observable.publish() if you want to multicast."));
            } else if(State.STATES.DISPOSED.ordinal() == state.state) {
                subscriber.onError(new IllegalStateException("The Content of this Observable is already released. "
                    + "Subscribe earlier or tune the CouchbaseEnvironment#autoreleaseAfter() setting."));
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
        state.bufferedSubject.onCompleted();
    }

    @Override
    public void onError(Throwable e) {
        state.bufferedSubject.onError(e);
    }

    @Override
    public void onNext(T t) {
        state.bufferedSubject.onNext(t);

        if (state.casTimeoutScheduled() && state.state == State.STATES.UNSUBSCRIBED.ordinal()) {
            state.setTimeoutSubscription(timeoutScheduler.subscribe(new Action1<Long>() { // Schedule timeout after the first content arrives.
                @Override
                public void call(Long aLong) {
                    disposeIfNotSubscribed();
                }
            }));
        }
    }

    @Override
    public boolean hasObservers() {
        return state.state == State.STATES.SUBSCRIBED.ordinal();
    }

}
