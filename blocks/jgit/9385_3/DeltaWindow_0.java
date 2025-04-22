	private static long estimateIndexSize(DeltaWindowEntry ent) {
		if (ent.buffer == null)
			return estimateSize(ent.object);

		int len = ent.buffer.length;
		return DeltaIndex.estimateIndexSize(len) - len;
	}

