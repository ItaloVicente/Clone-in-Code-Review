				if (o instanceof Repository) {
					repos.add((Repository) o);
				} else if (o instanceof PlatformObject) {
					Repository repo = CommonUtils.getAdapter(((PlatformObject) o), Repository.class);
					if (repo != null) {
						repos.add(repo);
					} else {
						return new Repository[0];
					}
