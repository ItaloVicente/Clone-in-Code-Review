
package com.couchbase.client.core.node;

import com.couchbase.client.core.utils.NetworkAddress;

public class NodeHostname {

    private final NetworkAddress logical;
    private final NetworkAddress physical;

    public NodeHostname(final NetworkAddress logical) {
        this(logical, null);
    }

    public NodeHostname(final NetworkAddress logical, final NetworkAddress physical) {
        if (logical == null) {
            throw new IllegalArgumentException("Logical network address can never be null!");
        }
        this.logical = logical;
        this.physical = physical == null ? logical : physical;
    }

    public NetworkAddress logical() {
        return logical;
    }

    public NetworkAddress physical() {
        return physical;
    }

    @Override
    public String toString() {
        if (physical != logical) {
            return logical.nameAndAddress() + "(" + physical.nameAndAddress() + ")";
        } else {
            return logical.nameAndAddress();
        }
    }
}
