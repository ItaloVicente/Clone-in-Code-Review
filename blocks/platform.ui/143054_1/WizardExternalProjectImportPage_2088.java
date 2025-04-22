		IPath path = new Path(projectFile.getPath());

		IProjectDescription newDescription = null;

		try {
			newDescription = getWorkspace().loadProjectDescription(path);
		} catch (CoreException exception) {
		}

		if (newDescription == null) {
			this.description = null;
			this.projectNameField.setText(""); //$NON-NLS-1$
		} else {
			this.description = newDescription;
			this.projectNameField.setText(this.description.getName());
		}
	}

	private File projectFile(String locationFieldContents) {
		File directory = new File(locationFieldContents);
		if (directory.isFile()) {
