package com.couchbase.client.java.env;

import com.couchbase.client.core.env.DefaultCoreEnvironment;
import com.couchbase.client.deps.io.netty.channel.EventLoopGroup;
import rx.Scheduler;

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
        public Builder sslEnabled(final boolean sslEnabled) {
            super.sslEnabled(sslEnabled);
            return this;
        }

        @Override
        public Builder sslKeystoreFile(final String sslKeystoreFile) {
            super.sslKeystoreFile(sslKeystoreFile);
            return this;
        }

        @Override
        public Builder sslKeystorePassword(final String sslKeystorePassword) {
            super.sslKeystorePassword(sslKeystorePassword);
            return this;
        }

        @Override
        public Builder queryEnabled(final boolean queryEnabled) {
            super.queryEnabled(queryEnabled);
            return this;
        }

        @Override
        public Builder queryPort(final int queryPort) {
            super.queryPort(queryPort);
            return this;
        }

        @Override
        public Builder bootstrapHttpEnabled(final boolean bootstrapHttpEnabled) {
            super.bootstrapHttpEnabled(bootstrapHttpEnabled);
            return this;
        }

        @Override
        public Builder bootstrapCarrierEnabled(final boolean bootstrapCarrierEnabled) {
            super.bootstrapCarrierEnabled(bootstrapCarrierEnabled);
            return this;
        }

        @Override
        public Builder bootstrapHttpDirectPort(final int bootstrapHttpDirectPort) {
            super.bootstrapHttpDirectPort(bootstrapHttpDirectPort);
            return this;
        }

        @Override
        public Builder bootstrapHttpSslPort(final int bootstrapHttpSslPort) {
            super.bootstrapHttpSslPort(bootstrapHttpSslPort);
            return this;
        }

        @Override
        public Builder bootstrapCarrierDirectPort(final int bootstrapCarrierDirectPort) {
            super.bootstrapCarrierDirectPort(bootstrapCarrierDirectPort);
            return this;
        }

        @Override
        public Builder bootstrapCarrierSslPort(final int bootstrapCarrierSslPort) {
            super.bootstrapCarrierSslPort(bootstrapCarrierSslPort);
            return this;
        }

        @Override
        public Builder ioPoolSize(final int ioPoolSize) {
            super.ioPoolSize(ioPoolSize);
            return this;
        }

        @Override
        public Builder computationPoolSize(final int computationPoolSize) {
            super.computationPoolSize(computationPoolSize);
            return this;
        }

        @Override
        public Builder requestBufferSize(final int requestBufferSize) {
            super.requestBufferSize(requestBufferSize);
            return this;
        }

        @Override
        public Builder responseBufferSize(final int responseBufferSize) {
            super.responseBufferSize(responseBufferSize);
            return this;
        }

        @Override
        public Builder binaryServiceEndpoints(final int binaryServiceEndpoints) {
            super.binaryServiceEndpoints(binaryServiceEndpoints);
            return this;
        }

        @Override
        public Builder viewServiceEndpoints(final int viewServiceEndpoints) {
            super.viewServiceEndpoints(viewServiceEndpoints);
            return this;
        }

        @Override
        public Builder queryServiceEndpoints(final int queryServiceEndpoints) {
            super.queryServiceEndpoints(queryServiceEndpoints);
            return this;
        }

        @Override
        public Builder ioPool(final EventLoopGroup group) {
            super.ioPool(group);
            return this;
        }

        @Override
        public Builder scheduler(final Scheduler scheduler) {
            super.scheduler(scheduler);
            return this;
        }

        @Override
        public DefaultCouchbaseEnvironment build() {
            return new DefaultCouchbaseEnvironment(this);
        }
    }
}
