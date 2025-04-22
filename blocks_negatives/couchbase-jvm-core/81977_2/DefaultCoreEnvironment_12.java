        /**
         * Set if DCP should be enabled (only makes sense with server versions >= 3.0.0, default {@value #DCP_ENABLED}).
         */
        @Deprecated
        public Builder dcpEnabled(final boolean dcpEnabled) {
            this.dcpEnabled = dcpEnabled;
            return this;
        }

