		if (this.defaultLocation != null) {
			String projectName = this.defaultLocation.getName();
			IWorkspaceRoot root = ResourcesPlugin.getWorkspace().getRoot();
			int i = 0;
			while (root.getProject(projectName).exists()) {
				projectName = this.defaultLocation.getName() + i;
			}
			this.mainPage.setInitialProjectName(projectName);
			this.mainPage.setInitialLocation(this.defaultLocation);
		}
