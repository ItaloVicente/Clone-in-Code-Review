package com.couchbase.client.java.query;

import com.couchbase.client.core.annotations.InterfaceStability;

@InterfaceStability.Uncommitted
public enum N1qlProfile {

    OFF {
        @Override
        public String toString() {
            return "off";
        }
    },

    PHASES {
        @Override
        public String toString() {
            return "phases";
        }
    },

    TIMINGS {
        @Override
        public String toString() {
            return "timings";
        }
    }
}
