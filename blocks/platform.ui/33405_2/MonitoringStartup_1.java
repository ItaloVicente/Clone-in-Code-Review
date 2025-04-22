		args.longEventThreshold = preferences.getInt(PLUGIN_ID, LONG_EVENT_THRESHOLD_MILLIS,
				500, null);
		args.maxStackSamples = preferences.getInt(PLUGIN_ID, MAX_STACK_SAMPLES, 3, null);
		args.sampleInterval = preferences.getInt(PLUGIN_ID, SAMPLE_INTERVAL_MILLIS, 300, null);
		args.initialSampleDelay = preferences.getInt(PLUGIN_ID, INITIAL_SAMPLE_DELAY_MILLIS,
				300, null);
		args.deadlockThreshold = preferences.getInt(PLUGIN_ID, DEADLOCK_REPORTING_THRESHOLD_MILLIS,
				600000, null);
		args.dumpAllThreads = preferences.getBoolean(PLUGIN_ID, DUMP_ALL_THREADS, false, null);
		args.filterTraces = preferences.getString(PLUGIN_ID, FILTER_TRACES, "", null); //$NON-NLS-1$
		args.logToErrorLog = preferences.getBoolean(PLUGIN_ID, LOG_TO_ERROR_LOG, true, null);
