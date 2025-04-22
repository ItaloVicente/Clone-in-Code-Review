		}
	}

	private String[] getCurrentBuildOrder() {
		if (notCheckedBuildOrder) {
			customBuildOrder = getWorkspace().getDescription().getBuildOrder();
			notCheckedBuildOrder = false;
		}

		return customBuildOrder;
	}

	private String[] getDefaultProjectOrder() {
		if (defaultBuildOrder == null) {
			IWorkspace workspace = getWorkspace();
			IWorkspace.ProjectOrder projectOrder = getWorkspace()
					.computeProjectOrder(workspace.getRoot().getProjects());
			IProject[] foundProjects = projectOrder.projects;
			defaultBuildOrder = new String[foundProjects.length];
			int foundSize = foundProjects.length;
			for (int i = 0; i < foundSize; i++) {
				defaultBuildOrder[i] = foundProjects[i].getName();
			}
		}

		return defaultBuildOrder;
	}

	private IWorkspace getWorkspace() {
		return ResourcesPlugin.getWorkspace();
	}

	private boolean includes(String[] testArray, String searchElement) {

		for (String currentSearchElement : testArray) {
			if (searchElement.equals(currentSearchElement)) {
