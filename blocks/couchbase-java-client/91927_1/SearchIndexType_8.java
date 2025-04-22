
package com.couchbase.client.java.cluster;

public enum SearchIndexType {

    INDEX {
        @Override
        public String toString() {
            return "fulltext-index";
        }
    },

    ALIAS {
        @Override
        public String toString() {
            return "fulltext-alias";
        }
    }

}
