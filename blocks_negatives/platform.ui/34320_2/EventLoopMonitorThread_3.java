		this.longEventThreshold = args.longEventThreshold;
		this.maxLoggedStackSamples = args.maxStackSamples;
		this.maxStackSamples = 2 * (args.maxStackSamples - 1);
		this.sampleInterval = args.sampleInterval;
		this.initialSampleDelay = args.initialSampleDelay;
		this.dumpAllThreads = args.dumpAllThreads;
		this.deadlockThreshold = args.deadlockThreshold;
		this.logToErrorLog = args.logToErrorLog;
