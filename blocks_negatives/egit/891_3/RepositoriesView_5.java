								.getConfiguredRepositories()) {
							try {
								Repository repo = repositoryCache
										.lookupRepository(new File(dir));
								repo
										.addRepositoryChangedListener(repositoryListener);
								repositories.add(repo);
							} catch (IOException e) {
								Activator.handleError(e.getMessage(), e, false);
							}
						}
