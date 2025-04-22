		removeListenerObject(listener);
	}

	protected void firePropertyChange(String changeId, Object oldValue, Object newValue) {
		final Object[] listeners = getListeners();

		if (listeners.length == 0) {
