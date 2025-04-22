		if (resource == null || !resource.isAccessible()) {
			return;
		}
		Job.getJobManager().cancel(JobFamilies.UPDATE_SELECTION);
		Job job = new Job(UIText.StagingView_GetRepo) {
			@Override
			protected IStatus run(IProgressMonitor monitor) {
				if (monitor.isCanceled()) {
					return Status.CANCEL_STATUS;
				}
				Repository newRep = getRepositoryOrNestedSubmoduleRepository(resource);
				if (newRep != null && newRep != currentRepository) {
					if (monitor.isCanceled()) {
						return Status.CANCEL_STATUS;
					}
					reload(newRep);
				}
				return Status.OK_STATUS;
			}

			@Override
			public boolean belongsTo(Object family) {
				return JobFamilies.UPDATE_SELECTION == family;
			}

			@Override
			public boolean shouldRun() {
				return shouldUpdateSelection();
			}
		};
		job.setSystem(true);
		schedule(job, false);
