	private void askToOpenProject(StagingEntry stagingEntry) {
		IResource resource = CommonUtils.getAdapterForObject(stagingEntry,
				IResource.class);
		if (resource != null) {
			IProject project = resource.getProject();
			if (!project.isOpen()) {
				if (MessageDialog.openQuestion(getSite().getShell(),
						UIText.StagingView_ProjectIsClosed,
						MessageFormat.format(UIText.StagingView_OpenProject,
								project.getName()))) {
					try {
						project.open(null);
					} catch (CoreException e) {
						Activator.handleError(
								UIText.StagingView_openingProjectFailed, e,
								true);
					}
				}
			}
		}
	}

