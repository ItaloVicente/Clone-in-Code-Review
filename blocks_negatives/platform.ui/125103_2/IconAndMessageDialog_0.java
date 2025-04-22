	/**
	 * Adds a listener for the {@link Link} that is used as message label.
	 *
	 * @param listener
	 */
	public void addLinkSelectionListener(SelectionListener listener) {
		if (this.linkSelectionListeners == null) {
			this.linkSelectionListeners = new ArrayList<>();
		}
		this.linkSelectionListeners.add(listener);
	}

