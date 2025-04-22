			File repoFile = new File(myCreatePage.getDirectory());
			if (!myCreatePage.getBare())
				repoFile = new File(repoFile, Constants.DOT_GIT);

			Repository repoToCreate = cache.lookupRepository(repoFile);
			repoToCreate.create(myCreatePage.getBare());
