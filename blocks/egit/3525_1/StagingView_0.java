	private void reload(final Repository repository) {
		final IndexDiff[] results = new IndexDiff[1];

		Job job = new Job(UIText.StagingView_IndexDiffReload) {
			@Override
			protected IStatus run(IProgressMonitor monitor) {
				IndexDiff indexDiff = doReload(repository);
				results[0] = indexDiff;
				return Status.OK_STATUS;
			}
		};

		job.addJobChangeListener(new JobChangeAdapter() {
			public void done(final IJobChangeEvent event) {
				form.getDisplay().asyncExec(new Runnable() {
					public void run() {
						if (form.isDisposed())
							return;

						IndexDiff indexDiff = results[0];
						unstagedTableViewer.setInput(new Object[] { repository,
								indexDiff });
						stagedTableViewer
								.setInput(new Object[] { repository, indexDiff });
						commitAction.setEnabled(repository.getRepositoryState()
								.canCommit());
						form.setText(StagingView.getRepositoryName(repository));
						updateCommitMessageComponent();
						clearCommitMessageToggles();
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
