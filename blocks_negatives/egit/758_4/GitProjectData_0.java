	private synchronized static Repository lookupRepository(final File gitDir)
			throws IOException {
		Reference<Repository> r = repositoryCache.get(gitDir);
		Repository d = r != null ? r.get() : null;
		if (d == null) {
			d = new Repository(gitDir);
			repositoryCache.put(gitDir, new WeakReference<Repository>(d));
		}
		prune(repositoryCache);
		return d;
	}

	private static <K, V> void prune(Map<K, Reference<V>> map) {
		for (final Iterator<Map.Entry<K, Reference<V>>> i = map.entrySet()
				.iterator(); i.hasNext();) {
			if (i.next().getValue().get() == null)
				i.remove();
		}
	}

