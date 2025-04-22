        /**
         * Toggles the N1QL Query feature (default value {@value #QUERY_ENABLED}).
         * This parameter will be deprecated once N1QL is in General Availability and shipped with the server.
         *
         * If not bundled with the server, the N1QL service must run on all the cluster's nodes.
         */
        public Builder queryEnabled(final boolean queryEnabled) {
            this.queryEnabled = queryEnabled;
            return this;
        }

        /**
         * Defines the port for N1QL Query (default value {@value #QUERY_PORT}).
         * This parameter will be deprecated once N1QL is in General Availability and shipped with the server.
         *
         * If not bundled with the server, the N1QL service must run on all the cluster's nodes.
         */
        public Builder queryPort(final int queryPort) {
            this.queryPort = queryPort;
            return this;
        }

