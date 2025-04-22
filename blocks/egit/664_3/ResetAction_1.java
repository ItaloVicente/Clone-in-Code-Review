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
