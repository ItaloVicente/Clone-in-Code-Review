		Pattern p = getPattern(filter);
		Matcher m = p.matcher(sortLabel);
		if (m.matches()) {
			int groupCount = m.groupCount();
			int[][] indices = new int[groupCount][];
			for (int i = 0; i < groupCount; i++) {
				int nGrp = i + 1;
				indices[i] = new int[] { m.start(nGrp), m.end(nGrp) - 1 };
			}
			int quality = QuickAccessEntry.MATCH_EXCELLENT;
			return new QuickAccessEntry(this, providerForMatching,
					indices,
					EMPTY_INDICES, quality );
		}
