		Reference<Repository> r = repositoryCache.get(gitDir);
		Repository d;
		if (r != null)
			d = r.get();
		else
			d = null;
		if (d == null) {
