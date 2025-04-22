			boolean pageComplete = viewer.getCheckedElements().length > 0;
			for (Object checkedElement : viewer.getCheckedElements()) {
				String path = ((ProjectAndRepo) checkedElement).getRepo();
				if (((ProjectAndRepo) checkedElement).getRepo() != null
					pageComplete = false;
				}
			}
			setPageComplete(pageComplete);
			for (IProject project : myWizard.projects) {
				if (ResourcesPlugin.getWorkspace().getRoot().getLocation()
						.isPrefixOf(project.getLocation())) {
					setMessage(
							UIText.ExistingOrNewPage_RepoCreationInWorkspaceCreationWarning,
							IMessageProvider.WARNING);
					break;
				}
