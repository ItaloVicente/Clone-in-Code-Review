				notifyListeners(activePart, selection, listeners);
				notifyListeners(activePart, selection, activePart.getSite().getId(),
						targetedListeners);
				notifyListeners(activePart, selection, postSelectionListeners);
				notifyListeners(activePart, selection, activePart.getSite().getId(),
						targetedPostSelectionListeners);
