					synchronized (loadedCommits) {
						if (commitToLoad != null) {
							loadedCommits.fillTo(commitToLoad, maxCommits);
							commitToShow = commitToLoad;
							commitToLoad = null;
							boolean commitFound = false;
							for (RevCommit commit : loadedCommits) {
								if (commit.getId().equals(commitToShow.getId())) {
									commitFound = true;
									break;
								}
							}
							commitNotFound = !commitFound;
						} else {
							loadedCommits.fillTo(oldsz + BATCH_SIZE - 1);
							if (oldsz == loadedCommits.size()) {
								forcedRedrawsAfterListIsCompleted++;
