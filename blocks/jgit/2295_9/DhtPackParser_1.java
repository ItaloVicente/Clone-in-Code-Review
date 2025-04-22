	private void putGlobalIndex(ProgressMonitor pm) throws DhtException {
		int objcnt = objectListByName.size();
		pm.beginTask(DhtText.get().recordingObjects

		int segments = Math.max(1
		linkIterators = newListIteratorArray(segments);

		int objsPerSegment = objcnt / segments;
		int beginIdx = 0;
		for (int i = 0; i < segments - 1; i++) {
			int endIdx = Math.min(beginIdx + objsPerSegment
			linkIterators[i] = objectListByName.subList(beginIdx
					.listIterator();
			beginIdx = endIdx;
