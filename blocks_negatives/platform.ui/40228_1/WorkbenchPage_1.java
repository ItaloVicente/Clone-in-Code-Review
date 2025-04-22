		List<ISelectionListener> listeners = targetedPostSelectionListeners.get(partId);
		if (listeners == null) {
			listeners = new ArrayList<ISelectionListener>();
			targetedPostSelectionListeners.put(partId, listeners);
		}
		listeners.add(listener);
		getWorkbenchWindow().getSelectionService().addPostSelectionListener(partId, listener);
