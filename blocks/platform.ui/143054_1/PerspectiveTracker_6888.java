	public void dispose() {
		if (window != null) {
			window.removePageListener(this);
			window.removePerspectiveListener(this);
			window = null;
		}
	}
