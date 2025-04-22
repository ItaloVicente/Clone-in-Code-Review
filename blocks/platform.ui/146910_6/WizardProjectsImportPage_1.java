	private void closeProjectsCheckboxClicked() {
		closeProjectsAfterImport = closeProjectsCheckbox.getSelection();
		if (closeProjectsAfterImport) {
			rebuildProjectsCheckbox.setEnabled(false);
			rebuildProjectsCheckbox.setSelection(false);
			rebuildProjectsAfterImport = false;
		} else {
			rebuildProjectsCheckbox.setEnabled(true);
		}
	}

