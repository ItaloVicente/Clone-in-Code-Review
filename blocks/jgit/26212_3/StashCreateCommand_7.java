
				if (includeUntracked) {
					for (DirCacheEntry entry : untracked) {
						File file = new File(repo.getWorkTree()
								entry.getPathString());
						FileUtils.delete(file);
					}
				}

