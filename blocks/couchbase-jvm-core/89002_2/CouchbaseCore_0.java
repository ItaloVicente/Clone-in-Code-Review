
package com.couchbase.client.core;

import com.couchbase.client.core.annotations.InterfaceAudience;
import com.couchbase.client.core.annotations.InterfaceStability;
import com.couchbase.client.core.env.CoreEnvironment;
import com.lmax.disruptor.RingBuffer;

@InterfaceAudience.Private
@InterfaceStability.Uncommitted
public class CoreContext {

    private final CoreEnvironment environment;
    private final RingBuffer<ResponseEvent> responseRingBuffer;

    public CoreContext(final CoreEnvironment environment,
        final RingBuffer<ResponseEvent> responseRingBuffer) {
        this.environment = environment;
        this.responseRingBuffer = responseRingBuffer;
    }

    public CoreEnvironment environment() {
        return environment;
    }

    public RingBuffer<ResponseEvent> responseRingBuffer() {
        return responseRingBuffer;
    }
}
