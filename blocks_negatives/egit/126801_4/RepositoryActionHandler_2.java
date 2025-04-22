		if (!selection.isEmpty()) {
			Set<Repository> repos = new LinkedHashSet<>();
			for (Object o : selection.toArray()) {
				Repository repo = AdapterUtils.adapt(o, Repository.class);
				if (repo != null) {
					repos.add(repo);
				} else {
					return new Repository[0];
				}
			}
			return repos.toArray(new Repository[0]);
		}
		return new Repository[0];
