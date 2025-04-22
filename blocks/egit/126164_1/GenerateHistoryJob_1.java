						commitNotFound = !commitFound;
					} else {
						loadedCommits.fillTo(oldsz + BATCH_SIZE - 1);
						if (oldsz == loadedCommits.size()) {
							forcedRedrawsAfterListIsCompleted++;
							break;
						}
