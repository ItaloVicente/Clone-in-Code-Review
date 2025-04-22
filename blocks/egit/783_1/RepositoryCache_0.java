	public synchronized Repository[] getAllReposiotries() {
		List<Repository> result = new ArrayList<Repository>();
		Collection<Reference<Repository>> values = repositoryCache.values();
		for(Reference<Repository> ref:values) {
			Repository repo = ref.get();
			if(repo!=null)
				result.add(repo);
		}
		return result.toArray(new Repository[result.size()]);
	}

