					if (subRepoBuilder == null) {
						subRepoBuilder = builder != null ? builder
								: new RepositoryBuilder();
					}
					try (Repository subRepo = SubmoduleWalk
							.getSubmoduleRepository(repository.getWorkTree()
									smw.getPath()
									subRepoBuilder)) {
