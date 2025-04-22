	private void initRepositoriesAndListeners() {
		synchronized (repositories) {
			repositories.clear();
			unregisterRepositoryListener();
			for (String dir : repositoryUtil.getConfiguredRepositories()) {
				try {
					Repository repo = repositoryCache
							.lookupRepository(new File(dir));
					myListeners.add(repo.getListenerList()
							.addIndexChangedListener(myIndexChangedListener));
					myListeners.add(repo.getListenerList()
							.addRefsChangedListener(myRefsChangedListener));
					myListeners.add(repo.getListenerList()
							.addConfigChangedListener(myConfigChangeListener));
					repositories.add(repo);
				} catch (IOException e) {
					Activator.handleError(e.getMessage(), e, false);
				}
