	private final List<IUiFreezeEventLogger> externalLoggers =
			new ArrayList<IUiFreezeEventLogger>();
	private final DefaultUiFreezeEventLogger defaultLogger;
	private final Tracer tracer;
	private final Display display;
	private final FilterHandler filterHandler;
	private final long initialSampleDelay;
	private final long sampleInterval;
	private final int maxStackSamples;
	private final int maxLoggedStackSamples;
	private final long deadlockThreshold;
	private final long uiThreadId;
	private final Object sleepMonitor;
	private final boolean dumpAllThreads;
	private final boolean logToErrorLog;
