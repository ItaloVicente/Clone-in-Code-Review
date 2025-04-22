	private AbstractVirtualTable table;

	private Object[] sentObjects = new Object[0];

	private IntHashMap knownIndices = new IntHashMap();

	private Object[] knownObjects = new Object[0];

	private static final int MIN_FLUSHLENGTH = 64;

	private int[] pendingClears = new int[MIN_FLUSHLENGTH];

	private int lastClear = 0;

	private volatile Range lastRange = new Range(0,0);

	private volatile boolean updateScheduled;

	private volatile boolean disposed = false;

	public static final class Range {
		int start = 0;
		int length = 0;

		public Range(int s, int l) {
			start = s;
			length = l;
		}
	}

	Runnable uiRunnable = () -> {
		updateScheduled = false;
		if(!table.getControl().isDisposed()) {
