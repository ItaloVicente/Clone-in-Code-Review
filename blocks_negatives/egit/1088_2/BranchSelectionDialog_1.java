		if (renameButton != null) {
			renameButton.setEnabled(branchSelected && !tagSelected);
		}

		if (newButton != null) {
			newButton.setEnabled(branchSelected && !tagSelected);
		}
