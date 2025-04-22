		IndexDiffCacheEntry entry;
		synchronized (entries) {
			File gitDir = new Path(repository.getDirectory().getAbsolutePath())
					.toFile();
			entry = entries.get(gitDir);
			if (entry != null) {
				return entry;
			}
			if (repository.isBare()) {
				return null;
			}
			entry = new IndexDiffCacheEntry(repository, globalListener);
			entries.put(gitDir, entry);
