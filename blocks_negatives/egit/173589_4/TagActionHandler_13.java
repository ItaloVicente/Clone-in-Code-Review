		Job tagJob = new Job(tagJobName) {
			@Override
			protected IStatus run(IProgressMonitor monitor) {
				try {
					new TagOperation(repo, tag, shouldMoveTag, isAnnotated)
							.execute(monitor);
				} catch (CoreException e) {
					return Activator.createErrorStatus(
							UIText.TagAction_taggingFailed, e);
				} finally {
					GitLightweightDecorator.refresh();
				}

				return Status.OK_STATUS;
			}

			@Override
			public boolean belongsTo(Object family) {
				return JobFamilies.TAG.equals(family)
						|| super.belongsTo(family);
			}
		};
