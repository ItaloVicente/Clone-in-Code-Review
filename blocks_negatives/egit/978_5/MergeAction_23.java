
	@Override
	public void execute(IAction action) {
		final Repository repository = getRepository(true);
		if (repository == null)
			return;

		if (!canMerge(repository))
			return;

		MergeTargetSelectionDialog mergeTargetSelectionDialog = new MergeTargetSelectionDialog(
				getShell(), repository);
		if (mergeTargetSelectionDialog.open() == IDialogConstants.OK_ID) {

			final String refName = mergeTargetSelectionDialog.getRefName();

			String jobname = NLS.bind(UIText.MergeAction_JobNameMerge, refName);
			Job job = new Job(jobname) {
				@Override
				protected IStatus run(IProgressMonitor monitor) {
					try {
						new MergeOperation(repository, refName).execute(monitor);
					} catch (final CoreException e) {
						getShell().getDisplay().asyncExec(new Runnable(){
							public void run() {
								Utils.handleError(getShell(), e, "Merge impossible", "Unsupported Operation"); //$NON-NLS-1$ //$NON-NLS-2$
							}});
					}
					return Status.OK_STATUS;
				}
			};
			job.setUser(true);
			job.schedule();

		}

	}

	private boolean canMerge(final Repository repository) {
		String message = null;
		try {
			Ref head = repository.getRef(Constants.HEAD);
			if (head == null || !head.isSymbolic())
				message = UIText.MergeAction_HeadIsNoBranch;
			else if (!repository.getRepositoryState().equals(
					RepositoryState.SAFE))
				message = NLS.bind(
						UIText.MergeAction_WrongRepositoryState,
						repository.getRepositoryState());
		} catch (IOException e) {
			Activator.logError(e.getMessage(), e);
			message = e.getMessage();
		}

		if (message != null) {
			MessageDialog.openError(getShell(),
					UIText.MergeAction_CannotMerge, message);
		}
		return (message == null);
