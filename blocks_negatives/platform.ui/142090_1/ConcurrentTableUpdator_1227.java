    /**
     * Creates a new table updator
     *
     * @param table real table to update
     */
    public ConcurrentTableUpdator(AbstractVirtualTable table) {
        this.table = table;
    }

    /**
     * Cleans up the updator object (but not the table itself).
     */
    public void dispose() {
    	disposed = true;
    }

    /**
     * True iff this object has been disposed.
     *
     * @return true iff dispose() has been called
     */
    public boolean isDisposed() {
    	return disposed;
    }

    /**
     * Returns the currently visible range
     *
     * @return the currently visible range
     */
    public Range getVisibleRange() {
    	return lastRange;
    }

    /**
     * Marks the given object as dirty. Will cause it to be cleared
     * in the table.
     *
     * @param toFlush
     */
    public void clear(Object toFlush) {
        synchronized(this) {
            int currentIdx = knownIndices.get(toFlush, -1);

            if (currentIdx == -1) {
                return;
            }

            pushClear(currentIdx);
        }

    }

    /**
     * Sets the size of the table. Called from a background thread.
     *
     * @param newTotal
     */
    public void setTotalItems(int newTotal) {
        synchronized (this) {
            if (newTotal != knownObjects.length) {
                if (newTotal < knownObjects.length) {
                    for (int i = newTotal; i < knownObjects.length; i++) {
                        Object toFlush = knownObjects[i];

                        if (toFlush != null) {
                        	knownIndices.remove(toFlush);
                        }
                    }
                }

                int minSize = Math.min(knownObjects.length, newTotal);

	            Object[] newKnownObjects = new Object[newTotal];
	            System.arraycopy(knownObjects, 0, newKnownObjects, 0, minSize);
	            knownObjects = newKnownObjects;

	            scheduleUIUpdate();
            }
        }
    }

    /**
     * Pushes an index onto the clear stack
     *
     * @param toClear row to clear
     */
    private void pushClear(int toClear) {

    	if (toClear >= sentObjects.length) {
    		return;
    	}

        if (sentObjects[toClear] == null) {
        	return;
        }

        sentObjects[toClear] = null;

        if (lastClear >= pendingClears.length) {
            int newCapacity = Math.min(MIN_FLUSHLENGTH, lastClear * 2);
            int[] newPendingClears = new int[newCapacity];
            System.arraycopy(pendingClears, 0, newPendingClears, 0, lastClear);
            pendingClears = newPendingClears;
        }

        pendingClears[lastClear++] = toClear;
    }

    /**
     * Sets the item on the given row to the given value. May be called from a background
     * thread. Schedules a UI update if necessary
     *
     * @param idx row to change
     * @param value new value for the given row
     */
    public void replace(Object value, int idx) {
        synchronized(this) {
            Object oldObject = knownObjects[idx];

            if (oldObject != value) {
            	if (oldObject != null) {
            		knownIndices.remove(oldObject);
            	}

                knownObjects[idx] = value;

            	if (value != null) {
	        		int oldIndex = knownIndices.get(value, -1);
	        		if (oldIndex != -1) {
	        			knownObjects[oldIndex] = null;
	        			pushClear(oldIndex);
	        		}

	        		knownIndices.put(value, idx);
            	}

                pushClear(idx);

                scheduleUIUpdate();
            }
        }
    }

    /**
     * Schedules a UI update. Has no effect if an update has already been
     * scheduled.
     */
    private void scheduleUIUpdate() {
        synchronized(this) {
	        if (!updateScheduled) {
	            updateScheduled = true;
	            if(!table.getControl().isDisposed()) {
