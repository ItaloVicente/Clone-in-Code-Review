		});
	}

	private void refreshProjectsListIfNeeded() {
		if (knownProjectsCount != ResourcesPlugin.getWorkspace().getRoot().getProjects().length) {
			refreshProjectsList();
		}
