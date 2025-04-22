
	public Set<String> getSkipWorktreePaths() {
		if (skipWorktreePaths == null) {
			HashSet<String> skipped = new HashSet<>();
			for (int i = 0; i < dirCache.getEntryCount(); i++) {
				DirCacheEntry dirCacheEntry = dirCache.getEntry(i);

				if (dirCacheEntry.isSkipWorkTree()) {
					skipped.add(dirCache.getEntry(i).getPathString());
				}
			}
			skipWorktreePaths = skipped;
		}
		return skipWorktreePaths;
	}
