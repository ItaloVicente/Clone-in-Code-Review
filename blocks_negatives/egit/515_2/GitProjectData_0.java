		final Iterator i = repositoryCache.entrySet().iterator();
		while (i.hasNext()) {
			final Map.Entry e = (Map.Entry) i.next();
			if (((Reference) e.getValue()).get() == null) {
				i.remove();
			}
		}

		final Repository d;
		if (repositoryCache.containsKey(gitDir)) {
			d = (Repository) repositoryCache.get(gitDir).get();
		} else {
