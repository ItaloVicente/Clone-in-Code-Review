		if (!selection.isEmpty()) {
			Set<Repository> repos = new LinkedHashSet<>();
			for (Object o : selection.toArray()) {
				Repository repo = AdapterUtils.adapt(o, Repository.class);
				if (repo != null) {
					repos.add(repo);
				}
			}
			return repos.toArray(new Repository[0]);
		}
		return new Repository[0];
