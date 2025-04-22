		Reference<Repository> r = repositoryCache.get(normalizedGitDir);
		Repository d = r != null ? r.get() : null;
		if (d == null) {
			d = FileRepositoryBuilder.create(normalizedGitDir);
			repositoryCache.put(normalizedGitDir,
					new WeakReference<Repository>(d));
