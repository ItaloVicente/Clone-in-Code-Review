		RevCommit commit = (RevCommit) getSelection(getPage())
				.getFirstElement();

		final Shell shell = getPart(event).getSite().getShell();
		final RevertCommitOperation op = new RevertCommitOperation(repo, commit);

		Job job = new Job(MessageFormat.format(UIText.RevertHandler_JobName,
				commit.name())) {
			@Override
			protected IStatus run(IProgressMonitor monitor) {
				try {
					op.execute(monitor);
					RevCommit newHead = op.getNewHead();
					List<Ref> revertedRefs = op.getRevertedRefs();
					if (newHead != null && revertedRefs.isEmpty())
						showRevertedDialog(shell);
					if (newHead == null)
						Activator
								.showError(UIText.RevertOperation_Failed, null);

				} catch (CoreException e) {
					Activator.handleError(UIText.RevertOperation_InternalError,
							e, true);
				}
				return Status.OK_STATUS;
			}

			@Override
			public boolean belongsTo(Object family) {
				if (JobFamilies.REVERT_COMMIT.equals(family))
					return true;
				return super.belongsTo(family);
			}
		};
		job.setUser(true);
		job.setRule(op.getSchedulingRule());
		job.schedule();
		return null;
	}

	private void showRevertedDialog(final Shell shell) {
		PlatformUI.getWorkbench().getDisplay().syncExec(new Runnable() {

			public void run() {
				MessageDialog.openWarning(shell,
