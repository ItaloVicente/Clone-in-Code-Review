			ISelectionService selectionService = getWorkbenchWindow().getSelectionService();
			for (ISelectionListener listener : selectionListeners) {
				selectionService.removeSelectionListener(listener);
			}

			for (Entry<String, List<ISelectionListener>> entries : targetedSelectionListeners
					.entrySet()) {
				String partId = entries.getKey();
				for (ISelectionListener listener : entries.getValue()) {
					selectionService.removeSelectionListener(partId, listener);
				}
			}

			for (ISelectionListener listener : postSelectionListeners) {
				selectionService.removePostSelectionListener(listener);
			}

			for (Entry<String, List<ISelectionListener>> entries : targetedPostSelectionListeners
					.entrySet()) {
				String partId = entries.getKey();
				for (ISelectionListener listener : entries.getValue()) {
					selectionService.removePostSelectionListener(partId, listener);
				}
			}

