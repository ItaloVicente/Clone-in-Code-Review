	private void add(Object[] toAdd) {
		changeQueue.enqueue(ChangeQueue.ADD, toAdd);
		makeDirty();
	}

	private void setContents(Object[] contents) {
		changeQueue.enqueue(ChangeQueue.SET, contents);
		makeDirty();
	}

	private void remove(Object[] toRemove) {
		changeQueue.enqueue(ChangeQueue.REMOVE, toRemove);
		makeDirty();
		refresh();
	}

	private void flush(Object[] toFlush, LazySortedCollection collection) {
		for (Object item : toFlush) {
			if (collection.contains(item)) {
				updator.clear(item);
			}
		}
	}


	private void update(Object[] items) {
		changeQueue.enqueue(ChangeQueue.UPDATE, items);
		makeDirty();
	}
