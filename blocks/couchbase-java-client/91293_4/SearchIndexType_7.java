
package com.couchbase.client.java.cluster;

import com.couchbase.client.core.annotations.InterfaceStability;

@InterfaceStability.Experimental
public enum SearchIndexSourceType {

    COUCHBASE {
        @Override
        public String toString() {
            return "couchbase";
        }
    },

    MEMCACHED {
        @Override
        public String toString() {
            return "memcached";
        }
    }
}
