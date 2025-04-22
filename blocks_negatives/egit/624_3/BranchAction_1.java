		try {
			getTargetPart().getSite().getWorkbenchWindow().run(true, false,
					new IRunnableWithProgress() {
				public void run(final IProgressMonitor monitor)
				throws InvocationTargetException {
					try {
						new BranchOperation(repository, refName).run(monitor);
						GitLightweightDecorator.refresh();
					} catch (final CoreException e) {
						if (GitTraceLocation.UI.isActive())
							GitTraceLocation.getTrace().trace(GitTraceLocation.UI.getLocation(), e.getMessage(), e);
						Display.getDefault().asyncExec(new Runnable() {
							public void run() {
								handle(
										new TeamException(e.getStatus()),
										UIText.BranchAction_errorSwitchingBranches,
										UIText.BranchAction_unableToSwitchBranches);
							}
						});
					}
