		this.display = getDisplay();
		this.uiThreadId = this.display.getThread().getId();
		this.filterHandler = new FilterHandler(args.filterTraces);
		this.samplingThreshold = args.samplingThreshold;
		this.minimumPollingDelay = args.minimumPollingDelay;
		this.loggedTraceCount = args.loggedTraceCount;
		this.maxTraceCount = 2 * (args.loggedTraceCount - 1);
		this.loggingThreshold = args.loggingThreshold;
