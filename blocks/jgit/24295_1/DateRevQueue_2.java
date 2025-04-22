	@Override
	boolean everbodyHasFlag(int f) {
		for (Iterator<RevCommit> it : bucketQueue.getIterators()) {
			while (it.hasNext()) {
				if ((it.next().flags & f) == 0)
					return false;
			}
