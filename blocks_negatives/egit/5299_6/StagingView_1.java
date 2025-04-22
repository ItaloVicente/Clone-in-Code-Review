		}
	}

	private void attachListeners(Repository repository) {
		myListeners.add(repository.getListenerList().addIndexChangedListener(
				myIndexChangedListener));
		myListeners.add(repository.getListenerList().addRefsChangedListener(
				myRefsChangedListener));
	}

	private void removeListeners() {
		for (ListenerHandle lh : myListeners)
			lh.remove();
		myListeners.clear();
