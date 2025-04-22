		}

		if (currentSelection == null && bootstrapSelection != null) {
			currentSelection = bootstrapSelection;
			bootstrapSelection = null;
		}

		updateSelectionInPage();
	}
