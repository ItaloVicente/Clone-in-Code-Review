		boolean useStagingView = Activator.getDefault().getPreferenceStore()
				.getBoolean(UIPreferences.ALWAYS_USE_STAGING_VIEW);
		if (useStagingView) {
			PlatformUI.getWorkbench().getDisplay().asyncExec(new Runnable() {

				@Override
				public void run() {
					try {
						StagingView view = (StagingView) PlatformUI.getWorkbench()
								.getActiveWorkbenchWindow().getActivePage()
								.showView(StagingView.VIEW_ID);
						view.setFocus();
					} catch (PartInitException e) {
						Activator.logError(e.getMessage(), e);
					}
				}
			});
		} else {
			final Shell shell = getShell(event);
			IResource[] resourcesInScope;
			try {
				IResource[] selectedResources = getSelectedResources(event);
				if (selectedResources.length > 0) {
					IWorkbenchPart part = getPart(event);
					resourcesInScope = GitScopeUtil.getRelatedChanges(part,
							selectedResources);
				} else
					resourcesInScope = new IResource[0];
			} catch (InterruptedException e) {
				return null;
			}
			CommitUI commitUi = new CommitUI(shell, repository,
					resourcesInScope, false);
			commitUi.commit();
		}
