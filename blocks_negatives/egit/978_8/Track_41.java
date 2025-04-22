
	@Override
	public void execute(IAction action) {
		final TrackOperation op = new TrackOperation(getSelectedResources());
		String jobname = UIText.Track_addToVersionControl;
		Job job = new Job(jobname) {
			@Override
			protected IStatus run(IProgressMonitor monitor) {
				try {
					op.execute(monitor);
				} catch (CoreException e) {
					return Activator.createErrorStatus(e.getStatus()
							.getMessage(), e);
				}
				return Status.OK_STATUS;
			}
		};
		job.setRule(op.getSchedulingRule());
		job.setUser(true);
		job.schedule();
	}

	@Override
	public boolean isEnabled() {
		return getSelectedAdaptables(getSelection(), IResource.class).length > 0;
