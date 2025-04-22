			String jobname = NLS.bind(UIText.ResetAction_reset, refName);
			Job job = new Job(jobname) {
				@Override
				protected IStatus run(IProgressMonitor monitor) {
					try {
						new ResetOperation(repository, refName, type)
								.execute(monitor);
						GitLightweightDecorator.refresh();
					} catch (CoreException e) {
						return Activator.createErrorStatus(e.getStatus()
								.getMessage(), e);
