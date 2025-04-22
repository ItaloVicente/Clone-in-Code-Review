
package com.couchbase.client.vbucket.provider;

public enum BootstrapProviderType {

    NONE,

    CARRIER,

    HTTP;

    public boolean isCarrier() {
        return this.ordinal() == CARRIER.ordinal();
    }

    public boolean isHttp() {
        return this.ordinal() == HTTP.ordinal();
    }
}
