		final Git git = new Git(currentRepository);

		Job resetJob = new Job(UIText.StagingView_ResetJob) {
			@Override
			protected IStatus run(IProgressMonitor monitor) {
				try {
					ResetCommand reset = git.reset();
					for (String path : paths)
						reset.addPath(path);
					reset.call();
				} catch (GitAPIException e) {
					Activator.handleError(e.getMessage(), e, true);
				}
				return Status.OK_STATUS;
			}

			@Override
			public boolean belongsTo(Object family) {
				return family == JobFamilies.RESET;
			}
		};
		schedule(resetJob, true);
