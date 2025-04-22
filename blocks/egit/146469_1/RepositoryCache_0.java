	public void removeRepository(Repository repo) {
		File normalizedGitDir = new Path(repo.getDirectory().getAbsolutePath())
				.toFile();
		Reference<Repository> ref = repositoryCache.remove(normalizedGitDir);
		Repository r = ref.get();
		if (r != null) {
			IndexDiffCache indexDiffCache = Activator.getDefault()
					.getIndexDiffCache();
			indexDiffCache.remove(r.getDirectory());
			r.close();
		}
	}

