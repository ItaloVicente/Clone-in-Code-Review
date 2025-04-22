		Job job = new WorkspaceJob(jobname) {

			@Override
			public IStatus runInWorkspace(IProgressMonitor monitor) {
				try {
					op.execute(monitor);
				} catch (final CoreException e) {
					return e.getStatus();
				}
				return Status.OK_STATUS;
			}
		};
