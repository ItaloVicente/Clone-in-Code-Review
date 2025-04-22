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
			final MergeOperation op = new MergeOperation(repository, refName);
			Job job = new Job(jobname) {
				@Override
				protected IStatus run(IProgressMonitor monitor) {
					try {
						op.execute(monitor);
					} catch (final CoreException e) {
						return e.getStatus();
					}
					return Status.OK_STATUS;
				}
			};
			job.setUser(true);
			job.addJobChangeListener(new JobChangeAdapter() {
				@Override
				public void done(IJobChangeEvent event) {
					IStatus result = event.getJob().getResult();
					if (result.getSeverity() == IStatus.CANCEL) {
						Display.getDefault().asyncExec(new Runnable() {
							public void run() {
								MessageDialog
										.openInformation(
												getShell(),
												UIText.MergeAction_MergeCanceledTitle,
												UIText.MergeAction_MergeCanceledMessage);
							}
						});
					} else if (!result.isOK()) {
						Activator.handleError(result.getMessage(), result
								.getException(), true);
					} else {
						Display.getDefault().asyncExec(new Runnable() {
							public void run() {
								MessageDialog.openInformation(getShell(),
										UIText.MergeAction_MergeResultTitle, op
												.getResult().toString());
							}
						});
					}
				}
			});
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
