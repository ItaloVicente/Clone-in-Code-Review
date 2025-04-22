package com.couchbase.client.java.env;

import com.couchbase.client.core.env.DefaultCoreEnvironment;

public class DefaultCouchbaseEnvironment extends DefaultCoreEnvironment implements CouchbaseEnvironment {

    private DefaultCouchbaseEnvironment(final Builder builder) {
       super(builder);
    }

    public static DefaultCouchbaseEnvironment create() {
        return builder().build();
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder extends DefaultCoreEnvironment.Builder {

        @Override
        public DefaultCouchbaseEnvironment build() {
            return new DefaultCouchbaseEnvironment(this);
        }
    }
}
