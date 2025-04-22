		longEventWarningThreshold = Math.max(args.longEventWarningThreshold, 3);
		longEventErrorThreshold = Math.max(args.longEventErrorThreshold, longEventWarningThreshold);
		maxLoggedStackSamples = Math.max(args.maxStackSamples, 0);
		maxStackSamples = 2 * maxLoggedStackSamples;
		sampleInterval = longEventWarningThreshold * 2 / 3;
		allThreadsSampleInterval = longEventErrorThreshold * 2 / 3;
		deadlockThreshold = args.deadlockThreshold;
		logToErrorLog = args.logToErrorLog;
