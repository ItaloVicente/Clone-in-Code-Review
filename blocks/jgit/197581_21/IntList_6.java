	public IntList(int beginInclusive
		this(endExclusive - beginInclusive);
		for (int val = beginInclusive; val < endExclusive; val++) {
			entries[val - beginInclusive] = val;
		}
		count = entries.length;
	}

