	}

	public void removeCheckStateListener(ICheckStateListener listener) {
		removeListenerObject(listener);
	}

	public void setAllSelections(final boolean selection) {
		if (root == null) {
