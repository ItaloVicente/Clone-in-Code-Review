        LogListener logListener = new SynchronousLogListener() {
            @Override
			public void logged(LogEntry entry) {
                if (entry.getLevel() == LogService.LOG_ERROR && entry.getException() instanceof ClassCastException
                    cces.add((ClassCastException) entry.getException());
                }
            }
        };
        LogFilter logFilter = new LogFilter() {
            @Override
			public boolean isLoggable(Bundle bundle, String loggerName, int logLevel) {
            }
        };
