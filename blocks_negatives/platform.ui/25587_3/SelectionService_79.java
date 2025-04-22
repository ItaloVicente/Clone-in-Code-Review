	@PreDestroy
	public void dispose() {
		selectionService = null;
	}

	private void notifyListeners(IWorkbenchPart workbenchPart, ISelection selection,
			ListenerList listenerList) {
		for (Object listener : listenerList.getListeners()) {
