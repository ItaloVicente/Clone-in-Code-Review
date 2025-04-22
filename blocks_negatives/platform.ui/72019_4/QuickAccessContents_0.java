					for (Iterator<QuickAccessEntry> iterator = poorFilterMatches.iterator(); iterator
							.hasNext()
							&& (showAllMatches || (count < countPerProvider && countTotal < maxCount));) {
						QuickAccessEntry quickAccessEntry = iterator.next();
						entries[i].add(quickAccessEntry);
						count++;
						countTotal++;
						if (i == 0 && quickAccessEntry.element == perfectMatch) {
							perfectMatchAdded = true;
							maxCount = MAX_COUNT_TOTAL;
						}
					}
