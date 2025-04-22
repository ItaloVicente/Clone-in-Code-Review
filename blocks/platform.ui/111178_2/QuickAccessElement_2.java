		index = combinedSortLabel.toLowerCase().indexOf(filter);
		if (index != -1) { // match
			index = combinedLabel.toLowerCase().indexOf(filter);
			if (index != -1) { // compute highlight on label
				int lengthOfElementMatch = index + filter.length() - providerForMatching.getName().length() - 1;
				if (lengthOfElementMatch > 0) {
					return new QuickAccessEntry(this, providerForMatching,
							new int[][] { { 0, lengthOfElementMatch - 1 } },
							new int[][] { { index, index + filter.length() - 1 } }, QuickAccessEntry.MATCH_GOOD);
				}
