	private void reload(final Repository repository) {
		final boolean repositoryChanged = currentRepository == repository;

		final AtomicReference<IndexDiff> results = new AtomicReference<IndexDiff>();

		final String jobTitle = MessageFormat.format(UIText.StagingView_IndexDiffReload,
				StagingView.getRepositoryName(repository));

		Job job = new Job(jobTitle) {
			@Override
			protected IStatus run(IProgressMonitor monitor) {
				IndexDiff indexDiff = doReload(repository, monitor, jobTitle);
				results.set(indexDiff);
				return Status.OK_STATUS;
			}
		};

		job.setUser(true);
		job.setRule(ResourcesPlugin.getWorkspace().getRoot());

		job.addJobChangeListener(new JobChangeAdapter() {
			public void done(final IJobChangeEvent event) {
				form.getDisplay().asyncExec(new Runnable() {
					public void run() {
						if (form.isDisposed())
							return;

						final IndexDiff indexDiff = results.get();
						unstagedTableViewer.setInput(new Object[] { repository,
								indexDiff });
						stagedTableViewer
								.setInput(new Object[] { repository, indexDiff });
						commitAction.setEnabled(repository.getRepositoryState()
								.canCommit());
						form.setText(StagingView.getRepositoryName(repository));
						if (repositoryChanged) {
							updateCommitMessageComponent();
							clearCommitMessageToggles();
						}
						updateSectionText();
					}

				});
			}
		});

		schedule(job);
	}

	private void schedule(final Job j) {
		IWorkbenchPartSite site = getSite();
		if (site != null) {
			final IWorkbenchSiteProgressService p;
			p = (IWorkbenchSiteProgressService) site
					.getAdapter(IWorkbenchSiteProgressService.class);
			if (p != null) {
				p.schedule(j, 0, true /* use half-busy cursor */);
				return;
			}
		}
		j.schedule();
	}

	private IndexDiff doReload(Repository repository, IProgressMonitor monitor, String jobTitle) {
