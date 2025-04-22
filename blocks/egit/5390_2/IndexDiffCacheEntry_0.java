	public void refreshResourcesAndIndexDiff() {
		String repositoryName = Activator.getDefault().getRepositoryUtil()
				.getRepositoryName(repository);
		String jobName = MessageFormat
				.format(CoreText.IndexDiffCacheEntry_refreshingProjects,
						repositoryName);
		Job job = new Job(jobName) {

			@Override
			protected IStatus run(IProgressMonitor monitor) {
				try {
					IProject[] validOpenProjects = ProjectUtil
							.getValidOpenProjects(repository);
					ProjectUtil.refreshResources(validOpenProjects, monitor);
				} catch (CoreException e) {
					return Activator.error(e.getMessage(), e);
				}
				refresh();
				return Status.OK_STATUS;
			}

		};
		job.setRule(ResourcesPlugin.getWorkspace().getRoot());
		job.schedule();
	}

