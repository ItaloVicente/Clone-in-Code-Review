		args.longEventThreshold = preferences.getInt(PreferenceConstants.LONG_EVENT_THRESHOLD_MILLIS);
		args.maxStackSamples = preferences.getInt(PreferenceConstants.MAX_STACK_SAMPLES);
		args.sampleInterval = preferences.getInt(PreferenceConstants.SAMPLE_INTERVAL_MILLIS);
		args.initialSampleDelay = preferences.getInt(PreferenceConstants.INITIAL_SAMPLE_DELAY_MILLIS);
		args.deadlockThreshold = preferences.getInt(PreferenceConstants.DEADLOCK_REPORTING_THRESHOLD_MILLIS);
		args.dumpAllThreads = preferences.getBoolean(PreferenceConstants.DUMP_ALL_THREADS);
		args.filterTraces = preferences.getString(PreferenceConstants.FILTER_TRACES);
		args.logToErrorLog = preferences.getBoolean(PreferenceConstants.LOG_TO_ERROR_LOG);
