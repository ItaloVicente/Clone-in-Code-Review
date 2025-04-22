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
				}

				@Override
				public boolean belongsTo(Object family) {
					if (JobFamilies.DISCARD_CHANGES.equals(family)) {
						return true;
					}
					return super.belongsTo(family);
				}
			};
			job.setUser(true);
			job.setRule(operation.getSchedulingRule());
			job.schedule();
			return null;
		} finally {
			mySelection = null;
		}
