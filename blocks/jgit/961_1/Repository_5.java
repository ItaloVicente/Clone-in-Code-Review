	public ListenerList getListenerList() {
		return myListeners;
	}

	public void fireEvent(RepositoryEvent<?> event) {
		event.setRepository(this);
		myListeners.dispatch(event);
		globalListeners.dispatch(event);
	}

