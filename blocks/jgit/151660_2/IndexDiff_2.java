					if (subRepoBuilder == null) {
						subRepoBuilder = new RepositoryBuilder();
					}
					try (Repository subRepo = SubmoduleWalk
							.getSubmoduleRepository(repository.getWorkTree()
									smw.getPath()
									subRepoBuilder)) {
