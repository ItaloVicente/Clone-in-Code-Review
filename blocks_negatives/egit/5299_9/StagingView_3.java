		final AtomicReference<IndexDiff> results = new AtomicReference<IndexDiff>();

		final String jobTitle = MessageFormat.format(UIText.StagingView_IndexDiffReload,
				StagingView.getRepositoryName(repository));

		if (reloadJob != null)
			reloadJob.cancel();
		reloadJob = new Job(jobTitle) {
			@Override
			protected IStatus run(IProgressMonitor monitor) {
				IndexDiff indexDiff = doReload(repository, monitor, jobTitle);
				results.set(indexDiff);
				if (monitor.isCanceled())
					return Status.CANCEL_STATUS;
				return Status.OK_STATUS;
			}

			@Override
			public boolean belongsTo(Object family) {
				if (family.equals(JobFamilies.STAGING_VIEW_REFRESH))
					return true;
				return super.belongsTo(family);
			}

		};
