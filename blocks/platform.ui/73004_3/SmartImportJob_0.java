			if (this.workspaceRoot.getProject(projectName).exists()) {
				StringBuilder projectNameBuilder = new StringBuilder(projectName);
				File currentDirectory = directory;
				while (currentDirectory.canRead() && directory.getParentFile() != null
						&& this.workspaceRoot.getProject(projectNameBuilder.toString()).exists()) {
					projectNameBuilder.insert(0, "_"); //$NON-NLS-1$
					projectNameBuilder.insert(0, currentDirectory.getParentFile().getName());
					currentDirectory = currentDirectory.getParentFile();
				}
				if (!this.workspaceRoot.getProject(projectNameBuilder.toString()).exists()) {
					projectName = projectNameBuilder.toString();
				} else {
					int i = 1;
					do {
						projectName = directory.getName() + '(' + i + ')';
						i++;
					} while (this.workspaceRoot.getProject(projectName).exists());
				}
