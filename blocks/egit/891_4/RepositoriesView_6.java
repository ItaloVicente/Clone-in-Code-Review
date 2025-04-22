	private void watchDirectory(String dir) {
		try {
			Repository repo = repositoryCache.lookupRepository(new File(dir));
			listenerHandlers.add(repo.getListenerList()
					.addIndexChangedListener(indexChangedListener));
			listenerHandlers.add(repo.getListenerList().addRefsChangedListener(
					refsChangedListener));
			repositories.add(repo);
		} catch (IOException e) {
			Activator.handleError(e.getMessage(), e, false);
		}
	}

