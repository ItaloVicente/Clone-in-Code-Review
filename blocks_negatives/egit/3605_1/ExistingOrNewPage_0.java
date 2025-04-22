			for (IProject project : myWizard.projects) {
				if (ResourcesPlugin.getWorkspace().getRoot().getLocation()
						.isPrefixOf(project.getLocation())) {
					setMessage(
							UIText.ExistingOrNewPage_RepoCreationInWorkspaceCreationWarning,
							IMessageProvider.WARNING);
					break;
				}
			}
