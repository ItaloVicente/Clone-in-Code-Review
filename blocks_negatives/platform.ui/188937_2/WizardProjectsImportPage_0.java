				nestedProjects = nestedProjectsCheckbox.getSelection();
				if (projectFromDirectoryRadio.getSelection()) {
					updateProjectsListAndPreventFocusLostHandling(directoryPathField.getText().trim(), true);
				} else {
					updateProjectsListAndPreventFocusLostHandling(archivePathField.getText().trim(), true);
				}
