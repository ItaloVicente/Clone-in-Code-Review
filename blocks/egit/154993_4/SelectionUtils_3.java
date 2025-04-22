				Repository repo = Adapters.adapt(o, Repository.class);
				if (repo != null) {
					repos.add(repo);
				} else {
					return new Repository[0];
				}
