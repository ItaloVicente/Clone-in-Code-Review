		if (!performAction)
			return;
		String jobname = UIText.DiscardChangesAction_discardChanges;
		Job job = new Job(jobname) {
			@Override
			protected IStatus run(IProgressMonitor monitor) {
				try {
					new DiscardChangesOperation(getSelectedResources())
							.execute(monitor);
				} catch (CoreException e) {
					return Activator.createErrorStatus(
							e.getStatus().getMessage(), e);
				}
				return Status.OK_STATUS;
