			if (resource != null) {
				showResource(resource);
			} else {
				Repository repo = AdapterUtils.adapt(firstElement,
						Repository.class);
				if (repo != null && currentRepository != repo) {
					reload(repo);
				}
			}
