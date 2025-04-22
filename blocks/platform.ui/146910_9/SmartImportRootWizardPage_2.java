	private void closeProjectsCheckboxClicked(boolean isClicked) {
		closeProjectsAfterImport = isClicked;
		if (isClicked) {
			rebuildProjectsCheckbox.setSelection(false);
			rebuildProjectsAfterImport = false;
		}
		rebuildProjectsCheckbox.setEnabled(!isClicked);
	}

