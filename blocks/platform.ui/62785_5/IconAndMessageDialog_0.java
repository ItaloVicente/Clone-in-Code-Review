	public void addLinkSelectionListener(SelectionListener listener) {
		if (this.linkSelectionListeners == null) {
			this.linkSelectionListeners = new ArrayList<>();
		}
		this.linkSelectionListeners.add(listener);
	}

