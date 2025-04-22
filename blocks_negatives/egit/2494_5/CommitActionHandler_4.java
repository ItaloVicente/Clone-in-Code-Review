		if (!PlatformUI.getWorkbench().saveAllEditors(true)) {
			return null;
		}

		BasicConfigurationDialog.show();
		resetState();
		final IProject[] projects = getProjectsInRepositoryOfSelectedResources(event);
		try {
			PlatformUI.getWorkbench().getProgressService().busyCursorWhile(new IRunnableWithProgress() {

				public void run(IProgressMonitor monitor) throws InvocationTargetException,
						InterruptedException {
					try {
						buildIndexHeadDiffList(projects, monitor);
					} catch (IOException e) {
						throw new InvocationTargetException(e);
					}
				}
			});
		} catch (InvocationTargetException e) {
			Activator.handleError(UIText.CommitAction_errorComputingDiffs, e.getCause(),
					true);
			return null;
		} catch (InterruptedException e) {
			return null;
		}

