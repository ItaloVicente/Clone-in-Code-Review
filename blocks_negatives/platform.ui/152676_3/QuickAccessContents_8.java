			}

			countPerProvider = 1;

		} while ((showAllMatches || countTotal < maxCount) && !done && !aMonitor.isCanceled());

		if (!perfectMatchAdded) {
			QuickAccessEntry entry = getMatcherFor(perfectMatch).match(filter, providers[0]);
			if (entry != null) {
				if (entries[0] == null) {
					entries[0] = new ArrayList<>();
					indexPerProvider[0] = 0;
				}
				entries[0].add(entry);
