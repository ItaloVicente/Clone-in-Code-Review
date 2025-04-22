
package com.couchbase.client.core.message.internal;

import com.couchbase.client.core.service.ServiceType;
import com.couchbase.client.core.state.LifecycleState;
import com.couchbase.client.core.utils.NetworkAddress;

import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.util.HashMap;
import java.util.Map;

public class EndpointHealth {

    private final ServiceType type;
    private final LifecycleState state;
    private final InetSocketAddress local;
    private final InetSocketAddress remote;
    private final long lastActivityUs;
    private final long pingLatencyUs;


    public EndpointHealth(ServiceType type, LifecycleState state, SocketAddress localAddr, SocketAddress remoteAddr, long lastActivityUs, long pingLatencyUs) {
        this.type = type;
        this.state = state;
        if (!(localAddr instanceof InetSocketAddress)) {
            throw new IllegalArgumentException("Right now only InetSocketAddress is supported");
        }
        if (!(remoteAddr instanceof InetSocketAddress)) {
            throw new IllegalArgumentException("Right now only InetSocketAddress is supported");
        }
        this.local = (InetSocketAddress) localAddr;
        this.remote = (InetSocketAddress) remoteAddr;
        this.lastActivityUs = lastActivityUs;
        this.pingLatencyUs = pingLatencyUs;
    }

    public ServiceType type() {
        return type;
    }

    public LifecycleState state() {
        return state;
    }

    public InetSocketAddress local() {
        return local;
    }

    public InetSocketAddress remote() {
        return remote;
    }

    public long lastActivity() {
        return lastActivityUs;
    }

    public long pingLatency() {
        return pingLatencyUs;
    }

    public Map<String, Object> toMap() {
        NetworkAddress ra = NetworkAddress.create(remote().getAddress().getHostAddress());
        NetworkAddress la = NetworkAddress.create(local().getAddress().getHostAddress());
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("remote", remote() == null ? "" : ra.nameOrAddress() + ":" + remote().getPort());
        map.put("local", local() == null ? "" : la.nameOrAddress() + ":" + local().getPort());
        map.put("state", state().toString().toLowerCase());
        map.put("last_activity_us", lastActivity());
        map.put("latency_us", pingLatency());
        return map;
    }

    @Override
    public String toString() {
        return "EndpointHealth{" +
            "type=" + type +
            ", state=" + state +
            ", local=" + local +
            ", remote=" + remote +
            ", lastActivity=" + lastActivityUs +
            '}';
    }
}
