    private AbstractVirtualTable table;

    /**
     * The array of objects that have been sent to the UI. Elements are null
     * if they either haven't been sent yet or have been scheduled for clear.
     * Maps indices onto elements.
     */
    private Object[] sentObjects = new Object[0];

    /**
     * Map of elements to object indices (inverse of the knownObjects array)
     */
    private IntHashMap knownIndices = new IntHashMap();

    /**
     * Contains all known objects that have been sent here from the background
     * thread.
     */
    private Object[] knownObjects = new Object[0];

    private static final int MIN_FLUSHLENGTH = 64;

    /**
     * Array of element indices. Contains elements scheduled to be
     * cleared. Only the beginning of the array is used. The number
     * of used elements is stored in lastClear
     */
    private int[] pendingClears = new int[MIN_FLUSHLENGTH];

    /**
     * Number of pending clears in the pendingClears array (this is normally
     * used instead of pendingClears.length since the
     * pendingClears array is usually larger than the actual number of pending
     * clears)
     */
    private int lastClear = 0;

    /**
     * Last known visible range
     */
    private volatile Range lastRange = new Range(0,0);

    /**
     * True iff a UI update has been scheduled
     */
    private volatile boolean updateScheduled;

    /**
     * True iff this object has been disposed
     */
    private volatile boolean disposed = false;

    /**
     * Object that holds a start index and length. Allows
     * the visible range to be returned as an atomic operation.
     */
    public final static class Range {
        int start = 0;
        int length = 0;

        /**
         * @param s
         * @param l
         */
        public Range(int s, int l) {
            start = s;
            length = l;
        }
    }

    /**
     * Runnable that can be posted with an asyncExec to schedule
     * an update to the real table.
     */
    Runnable uiRunnable = () -> {
	    updateScheduled = false;
	    if(!table.getControl().isDisposed()) {
