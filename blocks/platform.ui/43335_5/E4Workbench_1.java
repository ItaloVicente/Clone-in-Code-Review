	public void addWorkbenchListener(Object listener) {
		workbenchListeners.add(listener);
	}

	public void removeWorkbenchListener(Object listener) {
		workbenchListeners.remove(listener);
	}

	public Object[] getWorkbenchListeners() {
		return workbenchListeners.getListeners();
	}
