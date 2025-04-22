        if (!orphanResponseReportingEnabled) {
            orphanResponseReporter = null;
            orphanReporterShutdownHook = new NoOpShutdownHook();
        } else if (builder.orphanResponseReporter == null){
            orphanResponseReporter = DefaultOrphanResponseReporter.create();
            orphanReporterShutdownHook = new ShutdownHook() {
