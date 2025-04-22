
	@Override
	public void execute(IAction action) {

		boolean performAction = MessageDialog.openConfirm(getShell(),
				UIText.DiscardChangesAction_confirmActionTitle,
				UIText.DiscardChangesAction_confirmActionMessage);
		if (!performAction)
			return;
		final DiscardChangesOperation operation = new DiscardChangesOperation(
				getSelectedResources());
		String jobname = UIText.DiscardChangesAction_discardChanges;
		Job job = new Job(jobname) {
			@Override
			protected IStatus run(IProgressMonitor monitor) {
				try {
					operation.execute(monitor);
				} catch (CoreException e) {
					return Activator.createErrorStatus(e.getStatus()
							.getMessage(), e);
				}
				return Status.OK_STATUS;
			}
		};
		job.setUser(true);
		job.setRule(operation.getSchedulingRule());
		job.schedule();
