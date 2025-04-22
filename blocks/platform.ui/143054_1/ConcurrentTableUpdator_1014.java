	public ConcurrentTableUpdator(AbstractVirtualTable table) {
		this.table = table;
	}

	public void dispose() {
		disposed = true;
	}

	public boolean isDisposed() {
		return disposed;
	}

	public Range getVisibleRange() {
		return lastRange;
	}

	public void clear(Object toFlush) {
		synchronized(this) {
			int currentIdx = knownIndices.get(toFlush, -1);

			if (currentIdx == -1) {
				return;
			}

			pushClear(currentIdx);
		}

	}

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

	private void scheduleUIUpdate() {
		synchronized(this) {
			if (!updateScheduled) {
				updateScheduled = true;
				if(!table.getControl().isDisposed()) {
