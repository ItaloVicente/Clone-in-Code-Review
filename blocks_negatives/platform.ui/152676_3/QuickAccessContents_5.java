
					int j = indexPerProvider[providerIndex];
					while (j < sortedElements.length
							&& (showAllMatches || (count < countPerProvider && countTotal < maxCount))
							&& !aMonitor.isCanceled()) {
						QuickAccessElement element = sortedElements[j];

						if (!isPreviousPickProvider && prevPickIds.contains(element.getId())) {
							j++;
							continue;
						}

						QuickAccessEntry entry = null;
						if (filter.length() == 0) {
							if (providerIndex == 0 || showAllMatches) {
								entry = new QuickAccessEntry(element, provider, new int[0][0], new int[0][0],
										QuickAccessEntry.MATCH_PERFECT);
							} else {
								entry = null;
							}
						} else {
							QuickAccessEntry possibleMatch = getMatcherFor(element).match(filter, provider);
							if (possibleMatch != null) {
								entry = possibleMatch;
