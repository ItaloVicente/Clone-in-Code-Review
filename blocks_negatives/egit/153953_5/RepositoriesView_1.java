
		if (scheduledJob != null) {
			schedule(scheduledJob, delay);
			return;
		}


			@Override
			protected IStatus run(IProgressMonitor monitor) {
				final CommonViewer tv = getCommonViewer();
				if (!UIUtils.isUsable(tv)) {
					return Status.CANCEL_STATUS;
				}
				final boolean trace = GitTraceLocation.REPOSITORIESVIEW
						.isActive();
				final boolean needsNewInput = lastInputChange > lastInputUpdate;
				if (trace) {
					trace("Running the update, new input required: " //$NON-NLS-1$
									+ (lastInputChange > lastInputUpdate));
				}
				lastInputUpdate = System.currentTimeMillis();
				if (needsNewInput) {
					initRepositoriesAndListeners();
				}

				refreshUiJob.needsNewInput = needsNewInput;
				refreshUiJob.schedule();
				if (monitor.isCanceled()) {
					return Status.CANCEL_STATUS;
				}
				return Status.OK_STATUS;
			}

			@Override
			public boolean belongsTo(Object family) {
				return JobFamilies.REPO_VIEW_REFRESH.equals(family);
			}

		};
		job.setSystem(true);
		job.setUser(false);
		schedule(job, delay);
		scheduledJob = job;
