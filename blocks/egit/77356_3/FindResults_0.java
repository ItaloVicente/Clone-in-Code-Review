	public void addFindListener(IFindListener listener) {
		listeners.addIfAbsent(listener);
	}

	public void removeFindListener(IFindListener listener) {
		listeners.remove(listener);
