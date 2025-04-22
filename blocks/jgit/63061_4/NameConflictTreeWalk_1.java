	void stopWalk() throws IOException {
		if (!needsStopWalk()) {
			return;
		}

		for (;;) {
			AbstractTreeIterator t = min();
			if (t.eof()) {
				if (depth > 0) {
					exitSubtree();
					popEntriesEqual();
					continue;
				}
				return;
			}
			currentHead = t;
			skipEntriesEqual();
		}
	}

	private boolean needsStopWalk() {
		for (AbstractTreeIterator t : trees) {
			if (t.needsStopWalk()) {
				return true;
			}
		}
		return false;
	}

