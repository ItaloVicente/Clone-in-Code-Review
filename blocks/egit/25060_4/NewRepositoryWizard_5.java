			boolean isBare = myCreatePage.getBare();
			File gitDir = Git.init()
					.setDirectory(new File(myCreatePage.getDirectory()))
					.setBare(isBare)
					.call().getRepository().getDirectory();
			this.repository = Activator.getDefault().getRepositoryCache()
					.lookupRepository(gitDir);
