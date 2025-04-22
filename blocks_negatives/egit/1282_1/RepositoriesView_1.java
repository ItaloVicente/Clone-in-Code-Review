				if (needsNewInput) {
					synchronized (repositories) {
						unregisterRepositoryListener();
						repositories.clear();
						for (String dir : repositoryUtil
								.getConfiguredRepositories()) {
							try {
								Repository repo = repositoryCache
										.lookupRepository(new File(dir));
								myListeners.add(repo.getListenerList()
										.addIndexChangedListener(
												myIndexChangedListener));
								myListeners.add(repo.getListenerList()
										.addRefsChangedListener(
												myRefsChangedListener));
								repositories.add(repo);
							} catch (IOException e) {
								Activator.handleError(e.getMessage(), e, false);
							}
						}
					}
				}
