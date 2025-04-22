	private void listenToRepository(Repository repo) {
		myListeners.add(repo.getListenerList()
				.addIndexChangedListener(myIndexChangedListener));
		myListeners.add(repo.getListenerList()
				.addRefsChangedListener(myRefsChangedListener));
		myListeners.add(repo.getListenerList()
				.addConfigChangedListener(myConfigChangeListener));
	}

