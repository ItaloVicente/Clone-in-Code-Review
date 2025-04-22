			Repository repo = currentRepository;
			if (repo == null) {
				indexDiff = null;
			} else {
				indexDiff = doReload(repo);
			}
