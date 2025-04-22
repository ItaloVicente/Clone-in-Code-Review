	private static class EventHistory {
		private static final SimpleDateFormat TIME_FORMAT = new SimpleDateFormat("HH:mm:ss.SSS"); //$NON-NLS-1$

		private static class EventInfo {
			long timestamp;
			int eventType;
		}

		private final EventInfo[] buffer;
		private int start; // Index of the first recorded event.
		private int size;  // Number of recorded events.

		EventHistory(int capacity) {
			buffer = new EventInfo[capacity];
			for (int i = 0; i < capacity; i++) {
				buffer[i] = new EventInfo();
			}
		}

		synchronized void recordEvent(int eventType) {
			int j = (start + size) % buffer.length;
			EventInfo event = buffer[j];
			event.timestamp = System.currentTimeMillis();
			event.eventType = eventType;
			if (size < buffer.length) {
				size++;
			} else if (++start >= buffer.length) {
				start = 0;
			}
		}

		synchronized String extractAndClear() {
			StringBuilder buf = new StringBuilder();
			for (int i = 0; i < size; i++) {
				int j = (start + i) % buffer.length;
				EventInfo eventInfo = buffer[j];
				buf.append(TIME_FORMAT.format(new Date(eventInfo.timestamp)));
				buf.append(": "); //$NON-NLS-1$
				switch (eventInfo.eventType) {
				case SWT.PreEvent:
					buf.append("PreEvent"); //$NON-NLS-1$
					break;
				case SWT.PostEvent:
					buf.append("PostEvent"); //$NON-NLS-1$
					break;
				case SWT.Sleep:
					buf.append("Sleep"); //$NON-NLS-1$
					break;
				case SWT.Wakeup:
					buf.append("Wakeup"); //$NON-NLS-1$
					break;
				default:
					buf.append("Event "); //$NON-NLS-1$
					buf.append(eventInfo.eventType);
				}
				buf.append('\n');
			}
			size = 0;
			return buf.toString();
		}
	}

	private final EventLoopState eventLoopState = new EventLoopState();

	private volatile long eventStartOrResumeTime;

	private final int longEventThreshold;
	private final AtomicBoolean cancelled = new AtomicBoolean(false);
	private final AtomicReference<LongEventInfo> publishEvent =
			new AtomicReference<LongEventInfo>(null);

	private final List<IUiFreezeEventLogger> externalLoggers =
			new ArrayList<IUiFreezeEventLogger>();
	private final DefaultUiFreezeEventLogger defaultLogger;
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
	private EventHistory eventHistory;

