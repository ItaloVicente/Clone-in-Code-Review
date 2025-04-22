	@SuppressWarnings("unchecked")
	private void showResource(final IResource resource) {
		try {
			IProject project = resource.getProject();
			RepositoryMapping mapping = RepositoryMapping.getMapping(project);
			if (mapping == null)
				return;

			boolean added = repositoryUtil.addConfiguredRepository(mapping
					.getRepository().getDirectory());
			if (added) {
				scheduleRefresh();
			}

			boolean doSetSelection = false;

			if (this.scheduledJob != null) {
				int state = this.scheduledJob.getState();
				if (state == Job.WAITING || state == Job.RUNNING) {
					this.scheduledJob
							.addJobChangeListener(new JobChangeAdapter() {

								@Override
								public void done(IJobChangeEvent event) {
									showResource(resource);
								}
							});
				} else {
					doSetSelection = true;
				}
			}
