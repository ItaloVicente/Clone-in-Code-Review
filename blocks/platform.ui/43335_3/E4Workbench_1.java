	@Override
	public void addWorkbenchListener(Object listener) {
		workbenchListeners.add(listener);
	}

	@Override
	public void removeWorkbenchListener(Object listener) {
		workbenchListeners.remove(listener);
	}

	public List<Object> getWorkbenchListeners() {
		return workbenchListeners;
	}
