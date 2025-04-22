			index = getLabel().toLowerCase().indexOf(filter);
			if (index != -1) { // match actual label
				int quality = sortLabel.toLowerCase().equals(filter) ? QuickAccessEntry.MATCH_PERFECT
						: (sortLabel.toLowerCase().startsWith(filter) ? QuickAccessEntry.MATCH_EXCELLENT
								: QuickAccessEntry.MATCH_GOOD);
				return new QuickAccessEntry(this, providerForMatching,
						new int[][] { { index, index + filter.length() - 1 } },
	 EMPTY_INDICES, quality);
			}
			return new QuickAccessEntry(this, providerForMatching, EMPTY_INDICES, EMPTY_INDICES, QuickAccessEntry.MATCH_PARTIAL);
