	@Override
	public void dispose() {
		for (GitModelTree modelTree : cacheTreeMap.values())
			modelTree.dispose();

		cache.clear();
		cacheTreeMap.clear();
	}

