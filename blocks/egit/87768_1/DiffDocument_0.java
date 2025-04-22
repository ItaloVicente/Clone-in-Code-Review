	int getMaximumLineNumber(@NonNull DiffEntry.Side side) {
		if (maximumLineNumbers == null) {
			return DiffRegion.NO_LINE;
		}
		if (DiffEntry.Side.OLD.equals(side)) {
			return maximumLineNumbers[0];
		}
		return maximumLineNumbers[1];
	}

