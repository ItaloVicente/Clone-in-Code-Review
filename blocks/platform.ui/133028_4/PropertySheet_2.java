		if (currentPart == null && bootstrapSelection != null) {
			currentSelection = bootstrapSelection;
			selectionUpdatePending = true;
		} else {
			currentSelection = null;
		}
		bootstrapSelection = null;
		currentPart = part;
	}
