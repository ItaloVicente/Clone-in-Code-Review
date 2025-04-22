        /**
         * Sets the size of the buffer to control speed of DCP producer. The server will stop emitting data if
         * the current value of the buffer reach this limit. Set it to zero to disable DCP flow control.
         * (default value {@value #DCP_CONNECTION_BUFFER_SIZE}).
         */
        @Deprecated
        public Builder dcpConnectionBufferSize(final int dcpConnectionBufferSize) {
            this.dcpConnectionBufferSize = dcpConnectionBufferSize;
            return this;
        }

        /**
         * When a DCP connection read bytes reaches this percentage of the {@link #dcpConnectionBufferSize},
         * a DCP Buffer Acknowledge message is sent to the server to signal producer how much data has been processed.
         * (default value {@value #DCP_CONNECTION_BUFFER_ACK_THRESHOLD}).
         */
        @Deprecated
        public Builder dcpConnectionBufferAckThreshold(final double dcpConnectionBufferAckThreshold) {
            this.dcpConnectionBufferAckThreshold = dcpConnectionBufferAckThreshold;
            return this;
        }

        /**
         * Sets default name for DCP connection. It is used to identify streams on the server.
         * (default value {@value #DCP_CONNECTION_NAME}).
         */
        @InterfaceStability.Experimental
        @InterfaceAudience.Public
        @Deprecated
        public Builder dcpConnectionName(final String dcpConnectionName) {
            this.dcpConnectionName = dcpConnectionName;
            return this;
        }

