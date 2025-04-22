
		if (id != null) {
			Set<ISelectionListener> listeners = targetedListeners.get(id);
			if (listeners != null) {
				for (ISelectionListener listener : listeners) {
					if (selection != null || listener instanceof INullSelectionListener) {
						listener.selectionChanged(workbenchPart, selection);
					}
				}
			}
		}
