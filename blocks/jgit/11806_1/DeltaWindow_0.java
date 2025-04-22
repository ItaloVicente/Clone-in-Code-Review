		return NEXT_SRC;
	}

	private int deltaSizeLimit(DeltaWindowEntry src) {
		if (bestBase == null) {
			int n = res.size() >>> 1;
