			synchronized (lock) {
				hasMore = loadedCommits.isPending();
				size = loadedCommits.size();
				if (!hasMore) {
					incomplete = false;
				}
			}
