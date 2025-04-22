		final BranchOperation bop = getOperation(restore);

		Job job = new WorkspaceJob(jobname) {

			@Override
			public IStatus runInWorkspace(IProgressMonitor monitor) {
				try {
					bop.execute(monitor);
				} catch (CoreException e) {
					switch (bop.getResult().getStatus()) {
					case CONFLICTS:
					case NONDELETED:
						break;
					default:
						return Activator.createErrorStatus(
								UIText.BranchAction_branchFailed, e);
					}
				} finally {
					GitLightweightDecorator.refresh();
				}
				return Status.OK_STATUS;
			}

			@Override
			public boolean belongsTo(Object family) {
				if (JobFamilies.CHECKOUT.equals(family))
					return true;
				return super.belongsTo(family);
			}
		};
