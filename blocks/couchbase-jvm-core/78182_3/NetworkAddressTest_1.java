package com.couchbase.client.core.utils;

import java.net.InetAddress;

public class NetworkAddress {

    private static final boolean ALLOW_REVERSE_DNS = Boolean.parseBoolean(
        System.getProperty("com.couchbase.allowReverseDns", "true")
    );

    private final InetAddress inner;
    private final boolean createdFromHostname;
    private final boolean allowReverseDns;

    NetworkAddress(final String input, final boolean reverseDns) {
        try {
            this.inner = InetAddress.getByName(input);
            this.createdFromHostname = !InetAddresses.isInetAddress(input);
            this.allowReverseDns = reverseDns;
        } catch (Exception ex) {
            throw new IllegalArgumentException("Could not create NetworkAddress.", ex);
        }
    }

    private NetworkAddress(final String input) {
        this(input, ALLOW_REVERSE_DNS);
    }

    public static NetworkAddress create(final String input) {
        return new NetworkAddress(input);
    }

    public static NetworkAddress localhost() {
        return create("127.0.0.1");
    }

    public String hostname() {
        if (canUseHostname()) {
            return inner.getHostName();
        } else {
            throw new IllegalStateException("NetworkAddress not created from hostname " +
                "and reverse dns lookup disabled!");
        }
    }

    private boolean canUseHostname() {
        return allowReverseDns || createdFromHostname;
    }

    public String address() {
        return InetAddresses.toAddrString(inner);
    }

    public String nameOrAddress() {
        return canUseHostname() ? hostname() : address();
    }

    public String nameAndAddress() {
        String result = address();
        return canUseHostname() ? result + "/" + hostname() : result;
    }

    @Override
    public String toString() {
        return "NetworkAddress{" +
                 inner +
                ", fromHostname=" + createdFromHostname +
                ", reverseDns=" + allowReverseDns +
                '}';
    }
}
