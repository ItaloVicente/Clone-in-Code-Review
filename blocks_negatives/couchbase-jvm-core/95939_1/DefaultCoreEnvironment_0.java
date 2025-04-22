        if (!zombieResponseReportingEnabled) {
            zombieResponseReporter = null;
            zombieReporterShutdownHook = new NoOpShutdownHook();
        } else if (builder.zombieResponseReporter == null){
            zombieResponseReporter = DefaultZombieResponseReporter.create();
            zombieReporterShutdownHook = new ShutdownHook() {
