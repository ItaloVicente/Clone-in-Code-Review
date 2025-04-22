        @InterfaceAudience.Public
        @InterfaceStability.Committed
        public SELF zombieResponseReportingEnabled(final boolean zombieResponseReportingEnabled) {
            this.zombieResponseReportingEnabled = zombieResponseReportingEnabled;
            return self();
        }

        @InterfaceAudience.Public
        @InterfaceStability.Committed
        public SELF zombieResponseReporter(final ZombieResponseReporter zombieResponseReporter) {
            this.zombieResponseReporter = zombieResponseReporter;
            return self();
        }

