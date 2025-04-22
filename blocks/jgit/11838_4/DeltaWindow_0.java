		int split = e - halfRemaining;
		int h = toSearch[split].getPathHash();

		for (int n = split + 1; n < e; n++) {
			if (h != toSearch[n].getPathHash())
				return new DeltaTask.Slice(n
