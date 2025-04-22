        @InterfaceStability.Committed
        public SELF tracingEnabled(final boolean operationTracingEnabled) {
            this.operationTracingEnabled = operationTracingEnabled;
            return self();
        }

        @InterfaceStability.Committed
        public SELF operationTracingServerDurationEnabled(final boolean operationTracingServerDurationEnabled) {
            this.operationTracingServerDurationEnabled = operationTracingServerDurationEnabled;
