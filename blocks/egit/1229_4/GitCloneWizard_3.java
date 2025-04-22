	private void runAsJob(final URIish uri, final CloneOperation op) {
		final Job job = new Job(NLS.bind(UIText.GitCloneWizard_jobName,
				uri.toString())) {
			@Override
			protected IStatus run(final IProgressMonitor monitor) {
				try {
					return executeCloneOperation(op, monitor);
				} catch (InterruptedException e) {
					return Status.CANCEL_STATUS;
				} catch (InvocationTargetException e) {
					Throwable thr = e.getCause();
					return new Status(IStatus.ERROR, Activator.getPluginId(),
							0, thr.getMessage(), thr);
				}
