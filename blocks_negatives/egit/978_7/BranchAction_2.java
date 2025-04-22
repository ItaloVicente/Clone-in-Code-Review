	@Override
	public void execute(IAction action) throws InvocationTargetException, InterruptedException {
		final Repository repository = getRepository(true);
		if (repository == null)
			return;

		if (!repository.getRepositoryState().canCheckout()) {
			MessageDialog.openError(getShell(),
					UIText.BranchAction_cannotCheckout, NLS.bind(
							UIText.BranchAction_repositoryState, repository
									.getRepositoryState().getDescription()));
			return;
		}

		BranchSelectionDialog dialog = new BranchSelectionDialog(getShell(), repository);
		if (dialog.open() != Window.OK) {
			return;
		}

		final String refName = dialog.getRefName();

		String jobname = NLS.bind(UIText.BranchAction_checkingOut, refName);
		Job job = new Job(jobname) {
			@Override
			protected IStatus run(IProgressMonitor monitor) {
				try {
					new BranchOperation(repository, refName).execute(monitor);
				} catch (CoreException e) {
					return Activator.createErrorStatus(
							UIText.BranchAction_branchFailed, e);
				} finally {
					GitLightweightDecorator.refresh();
				}
				return Status.OK_STATUS;
			}
		};
		job.setUser(true);
		job.schedule();
	}

	@Override
	public boolean isEnabled() {
		return getRepository(false) != null;
