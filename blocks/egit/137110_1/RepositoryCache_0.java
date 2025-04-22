			Repository result = r != null ? r.get() : null;
			if (result != null && result.getDirectory().exists()) {
				return result;
			}
			repositoryCache.remove(normalizedGitDir);
		}
		IndexDiffCache cache = Activator.getDefault().getIndexDiffCache();
		if (cache != null) {
			cache.remove(normalizedGitDir);
