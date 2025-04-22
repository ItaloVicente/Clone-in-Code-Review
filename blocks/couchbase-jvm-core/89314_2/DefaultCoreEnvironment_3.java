        @InterfaceAudience.Public
        @InterfaceStability.Experimental
        public SELF tracingEnabled(final boolean tracingEnabled) {
            this.tracingEnabled = tracingEnabled;
            return self();
        }

        @InterfaceAudience.Public
        @InterfaceStability.Experimental
        public SELF tracer(final io.opentracing.Tracer tracer) {
            this.tracer = tracer;
            return self();
        }

