	private static String generateNewProjectName(File directory, IWorkspaceRoot root) {
		String projectName = directory.getName();
		if (root.getProject(projectName).exists()) {
			StringBuilder projectNameBuilder = new StringBuilder(projectName);
			File currentDirectory = directory;
			while (currentDirectory.canRead() && directory.getParentFile() != null
					&& root.getProject(projectNameBuilder.toString()).exists()) {
				projectNameBuilder.insert(0, "_"); //$NON-NLS-1$
				projectNameBuilder.insert(0, currentDirectory.getParentFile().getName());
				currentDirectory = currentDirectory.getParentFile();
			}
			if (!root.getProject(projectNameBuilder.toString()).exists()) {
				projectName = projectNameBuilder.toString();
			} else {
				int i = 1;
				do {
					projectName = directory.getName() + '(' + i + ')';
					i++;
				} while (root.getProject(projectName).exists());
			}
		}
		return projectName;
	}

