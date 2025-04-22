		List<Repository> result = new ArrayList<Repository>();
		Collection<Reference<Repository>> values = repositoryCache.values();
		for(Reference<Repository> ref:values) {
			Repository repo = ref.get();
			if(repo!=null)
				result.add(repo);
