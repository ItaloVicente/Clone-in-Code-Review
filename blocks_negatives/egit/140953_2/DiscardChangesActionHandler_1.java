			String jobname = UIText.DiscardChangesAction_discardChanges;
			Job job = new WorkspaceJob(jobname) {
				@Override
				public IStatus runInWorkspace(IProgressMonitor monitor) {
					try {
						operation.execute(monitor);
					} catch (CoreException e) {
						return Activator.createErrorStatus(
								e.getStatus().getMessage(), e);
					}
					return Status.OK_STATUS;
