	public int[] getMaximumLineNumbers() {
		return maximumLineNumbers.clone();
	}

	protected DiffRegion addRegion(@NonNull Type type, int start, int end) {
		return addRegion(type, start, end, DiffRegion.NO_LINE,
				DiffRegion.NO_LINE);
	}

