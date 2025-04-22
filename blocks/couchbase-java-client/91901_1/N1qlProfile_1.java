package com.couchbase.client.java.query;

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
