
	@Override
	public void execute(IAction action) {
		final Repository repository = getRepository(true);
		if (repository == null)
			return;
		if (!repository.getRepositoryState().canResetHead()) {
			MessageDialog.openError(getShell(), UIText.ResetAction_errorResettingHead,
					NLS.bind(UIText.ResetAction_repositoryState, repository.getRepositoryState().getDescription()));
			return;
		}
		ResetTargetSelectionDialog branchSelectionDialog = new ResetTargetSelectionDialog(getShell(), repository);
		if (branchSelectionDialog.open() == IDialogConstants.OK_ID) {
			final String refName = branchSelectionDialog.getRefName();
			final ResetType type = branchSelectionDialog.getResetType();
			String jobname = NLS.bind(UIText.ResetAction_reset, refName);
			final ResetOperation operation = new ResetOperation(repository,
					refName, type);
			Job job = new Job(jobname) {
				@Override
				protected IStatus run(IProgressMonitor monitor) {
					try {
						operation.execute(monitor);
						GitLightweightDecorator.refresh();
					} catch (CoreException e) {
						return Activator.createErrorStatus(e.getStatus()
								.getMessage(), e);
					}
					return Status.OK_STATUS;
				}
			};
			job.setRule(operation.getSchedulingRule());
			job.setUser(true);
			job.schedule();
		}
	}

	@Override
	public boolean isEnabled() {
		return getRepository(false) != null;
