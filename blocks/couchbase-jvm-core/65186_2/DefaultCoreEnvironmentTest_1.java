package com.couchbase.client.core.event.system;

import com.couchbase.client.core.event.CouchbaseEvent;
import com.couchbase.client.core.event.EventType;
import com.couchbase.client.core.utils.Events;

import java.util.Map;

public class TooManyEnvironmentsEvent implements CouchbaseEvent {

    private final int numEnvs;

    public TooManyEnvironmentsEvent(int numEnvs) {
        this.numEnvs = numEnvs;
    }

    @Override
    public EventType type() {
        return EventType.SYSTEM;
    }

    public int numEnvs() {
        return numEnvs;
    }

    @Override
    public Map<String, Object> toMap() {
        Map<String, Object> result = Events.identityMap(this);
        result.put("numEnvs", numEnvs);
        return result;
    }

    @Override
    public String toString() {
        return "TooManyEnvironmentsEvent{" +
                "numEnvs=" + numEnvs +
                '}';
    }
}
