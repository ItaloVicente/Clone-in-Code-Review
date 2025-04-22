package com.couchbase.client.core.state;

import rx.Subscriber;
import rx.Subscription;

import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public abstract class AbstractStateZipper<T, S extends Enum>
    extends AbstractStateMachine<S>
    implements StateZipper<T, S> {

    private final Map<T, Subscription> subscriptions;
    private final Map<T, S> states;
    private final S initialState;

    protected AbstractStateZipper(S initialState) {
        super(initialState);
        this.initialState = initialState;
        this.subscriptions = new ConcurrentHashMap<T, Subscription>();
        this.states = new ConcurrentHashMap<T, S>();
    }

    protected abstract S zipWith(Collection<S> states);

    @Override
    public void register(final T identifier, final Stateful<S> upstream) {
        Subscription subscription = upstream.states().subscribe(new Subscriber<S>() {
            @Override
            public void onCompleted() {
                deregister(identifier);
            }

            @Override
            public void onError(Throwable error) {
                deregister(identifier);
            }

            @Override
            public void onNext(S state) {
                states.put(identifier, state);
                transitionStateThroughZipper();
            }
        });
        subscriptions.put(identifier, subscription);
    }

    @Override
    public void deregister(final T identifier) {
        if (identifier == null) {
            return;
        }

        Subscription subscription = subscriptions.get(identifier);
        if (subscription != null && !subscription.isUnsubscribed()) {
            subscription.unsubscribe();
            subscriptions.remove(identifier);
            states.remove(identifier);
            transitionStateThroughZipper();
        }
    }

    @Override
    public void terminate() {
        Iterator<T> iterator = subscriptions.keySet().iterator();
        while (iterator.hasNext()) {
            T identifier = iterator.next();
            Subscription subscription = subscriptions.get(identifier);
            if (subscription != null && !subscription.isUnsubscribed()) {
                subscription.unsubscribe();
                iterator.remove();
                states.remove(identifier);
            }
        }
        transitionStateThroughZipper();
    }

    private void transitionStateThroughZipper() {
        Collection<S> currentStates = states.values();
        if (currentStates.isEmpty()) {
            transitionState(initialState);
        } else {
            transitionState(zipWith(states.values()));
        }
    }

    protected Map<T, Subscription> currentSubscriptions() {
        return subscriptions;
    }

    protected Map<T, S> currentStates() {
        return states;
    }

}
