		String jobname = UIText.CommitAction_CommittingChanges;
		Job job = new Job(jobname) {
			@Override
			protected IStatus run(IProgressMonitor monitor) {
				try {
					commitOperation.execute(monitor);

					for (IProject proj : getProjectsForSelectedResources()) {
						RepositoryMapping.getMapping(proj).fireRepositoryChanged();
					}
				} catch (CoreException e) {
					return Activator.createErrorStatus(
							UIText.CommitAction_CommittingFailed, e);
				} finally {
					GitLightweightDecorator.refresh();
				}
				return Status.OK_STATUS;
			}
		};
		job.setUser(true);
		job.schedule();
