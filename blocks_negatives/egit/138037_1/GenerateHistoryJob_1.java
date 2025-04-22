						commitNotFound = !commitFound;
					} else {
						loadedCommits.fillTo(oldsz + BATCH_SIZE - 1);
						if (oldsz == loadedCommits.size()) {
							forcedRedrawsAfterListIsCompleted++;
							break;
						}
					}
					if (monitor.isCanceled())
						return Status.CANCEL_STATUS;
					if (loadedCommits.size() > itemToLoad + (BATCH_SIZE / 2) + 1 && loadIncrementally)
						break;
					if (maxCommits > 0 && loadedCommits.size() > maxCommits) {
						incomplete = true;
