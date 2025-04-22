        Runtime.getRuntime().addShutdownHook(new Thread("cb-shutdown-pd") {
            @Override
            public void run() {
                PAUSE_DETECTOR.shutdown();
            }
        });
