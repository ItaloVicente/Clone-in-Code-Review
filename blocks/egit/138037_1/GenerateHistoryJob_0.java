			int listSize = 0;
			for (boolean hasMore = true; hasMore;) {
				if (trace) {
					GitTraceLocation.getTrace().trace(
							GitTraceLocation.HISTORYVIEW.getLocation(),
							"Filling commit list"); //$NON-NLS-1$
				}
				synchronized (loadedCommits) {
					try {
						if (!loadedCommits.isPending()) {
							return Status.CANCEL_STATUS;
						}
						if (commitToLoad != null) {
							loadedCommits.fillTo(commitToLoad, maxCommits);
							hasMore = loadedCommits.isPending();
							listSize = loadedCommits.size();
							commitToShow = commitToLoad;
							commitToLoad = null;
							boolean commitFound = false;
							for (RevCommit commit : loadedCommits) {
								if (commit.getId()
										.equals(commitToShow.getId())) {
									commitFound = true;
									break;
								}
							}
							commitNotFound = !commitFound;
						} else {
							int oldsz = loadedCommits.size();
							loadedCommits.fillTo(oldsz + BATCH_SIZE - 1);
							hasMore = loadedCommits.isPending();
							listSize = loadedCommits.size();
							if (oldsz == listSize) {
								forcedRedrawsAfterListIsCompleted++;
