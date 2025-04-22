					synchronized (loadedCommits) {
						if (commitToLoad != null) {
							loadedCommits.fillTo(commitToLoad, maxCommits);
							commitToLoad = null;
						} else
							loadedCommits.fillTo(oldsz + BATCH_SIZE - 1);
