
package com.couchbase.client.core.config;

import com.couchbase.client.core.utils.NetworkAddress;
import io.netty.util.internal.ObjectUtil;

public class ProposedBucketConfigContext {

    private final String bucketName;
    private final String config;
    private final NetworkAddress origin;

    public ProposedBucketConfigContext(final String bucketName, final String config, final NetworkAddress origin) {
        ObjectUtil.checkNotNull(bucketName, "bucket name cannot be null!");
        ObjectUtil.checkNotNull(config, "the raw config cannot be null!");
        this.bucketName = bucketName;
        this.config = config;
        this.origin = origin;
    }
    
    public String bucketName() {
        return bucketName;
    }

    public String config() {
        return config;
    }

    public NetworkAddress origin() {
        return origin;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ProposedBucketConfigContext that = (ProposedBucketConfigContext) o;

        if (bucketName != null ? !bucketName.equals(that.bucketName) : that.bucketName != null) {
            return false;
        }
        if (config != null ? !config.equals(that.config) : that.config != null) {
            return false;
        }
        return origin != null ? origin.equals(that.origin) : that.origin == null;
    }

    @Override
    public int hashCode() {
        int result = bucketName != null ? bucketName.hashCode() : 0;
        result = 31 * result + (config != null ? config.hashCode() : 0);
        result = 31 * result + (origin != null ? origin.hashCode() : 0);
        return result;
    }
}
