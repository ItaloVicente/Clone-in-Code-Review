					.setBare(isBare);
			if (myCreatePage.getSeparateGitDir()) {
				separateGitDir = true;
				init.setGitDir(FileUtils
						.canonicalize(new File(myCreatePage.getDirectory())))
						.setDirectory(FileUtils.canonicalize(
								new File(myCreatePage.getWorkingTree())));
			} else {
				init.setDirectory(FileUtils
						.canonicalize(new File(myCreatePage.getDirectory())));
			}
			File gitDir = init.call().getRepository().getDirectory();
			this.repository = RepositoryCache.INSTANCE
					.lookupRepository(gitDir);
