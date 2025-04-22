		display = getDisplay();
		uiThreadId = this.display.getThread().getId();
		this.longEventThreshold = args.longEventThreshold;
		this.maxLoggedStackSamples = args.maxStackSamples;
		this.maxStackSamples = 2 * (args.maxStackSamples - 1);
		this.sampleInterval = args.sampleInterval;
		this.initialSampleDelay = args.initialSampleDelay;
