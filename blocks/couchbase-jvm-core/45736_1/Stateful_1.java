package com.couchbase.client.core.state;

public interface StateZipper<T, S extends Enum> extends Stateful<S> {

    void register(T identifier, Stateful<S> stateful);

    void deregister(T identifier);

    void terminate();
}
