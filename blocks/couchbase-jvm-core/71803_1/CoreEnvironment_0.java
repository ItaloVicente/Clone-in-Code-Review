
package com.couchbase.client.core.env;

import com.couchbase.client.core.node.LegacyMemcachedHashingStrategy;
import com.couchbase.client.core.node.MemcachedHashingStrategy;

public interface BootstrapEnvironment {
    MemcachedHashingStrategy memcachedHashingStrategy();

}
