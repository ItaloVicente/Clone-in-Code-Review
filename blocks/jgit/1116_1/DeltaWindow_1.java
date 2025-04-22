	private static long estimateSize(ObjectToPack ent) {
		return DeltaIndex.estimateIndexSize(ent.getWeight());
	}

	private void clear(DeltaWindowEntry ent) {
		if (ent.index != null)
			loaded -= ent.index.getIndexSize();
		else if (res.buffer != null)
			loaded -= ent.buffer.length;
		ent.set(null);
	}

