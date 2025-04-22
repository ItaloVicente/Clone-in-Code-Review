
	private static class UnmapJob extends Job {

		private final IProject project;

		private UnmapJob(IProject project) {
			super(MessageFormat.format(CoreText.GitProjectData_UnmapJobName,
					project.getName()));
			this.project = project;
		}

		@Override
		protected IStatus run(IProgressMonitor monitor) {
			try {
				RepositoryProvider.unmap(project);
				return Status.OK_STATUS;
			} catch (TeamException e) {
				return new Status(IStatus.ERROR, Activator.getPluginId(),
						CoreText.GitProjectData_UnmappingGoneResourceFailed, e);
			}
		}
	}
