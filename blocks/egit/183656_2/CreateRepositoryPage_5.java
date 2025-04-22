	public boolean getSeparateGitDir() {
		return separateButton != null && separateButton.getSelection();
	}

	protected void updateControls() {
		if (getSeparateGitDir()) {
			if (project != null) {
				workingTreeText.setText(project.getLocation().toOSString());
			}
			workingTreeText.setEnabled(true);
			workingTreeBrowseButton.setEnabled(true);

		} else {
			workingTreeText.setEnabled(false);
			workingTreeBrowseButton.setEnabled(false);
			workingTreeText.setText(directoryText.getText());
		}
	}

