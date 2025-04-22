	private void setNestedProjects(boolean nestedProjects) {
		this.nestedProjects = nestedProjects;
		if (projectFromDirectoryRadio.getSelection()) {
			updateProjectsListAndPreventFocusLostHandling(directoryPathField.getText().trim(), true);
		} else {
			updateProjectsListAndPreventFocusLostHandling(archivePathField.getText().trim(), true);
		}
	}

	private void setCopyFiles(boolean copyFiles) {
		this.copyFiles = copyFiles;
		projectsList.refresh(true);
	}

