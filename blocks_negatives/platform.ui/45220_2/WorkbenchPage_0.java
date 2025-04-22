		List<ISelectionListener> listeners = targetedSelectionListeners.get(partId);
		if (listeners == null) {
			listeners = new ArrayList<ISelectionListener>();
			targetedSelectionListeners.put(partId, listeners);
		}
		listeners.add(listener);
		getWorkbenchWindow().getSelectionService().addSelectionListener(partId, listener);
