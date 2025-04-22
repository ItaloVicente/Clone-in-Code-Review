
		commitJob.addJobChangeListener(new JobChangeAdapter() {
			@Override
			public void done(IJobChangeEvent event) {
				PlatformUI.getWorkbench().getDisplay()
						.asyncExec(new Runnable() {
							public void run() {
								enableCommitWidgets(true);
							}
						});
			}
		});

		enableCommitWidgets(false);
		IWorkbenchSiteProgressService service = (IWorkbenchSiteProgressService) getSite()
				.getService(IWorkbenchSiteProgressService.class);
		if (service != null)
			service.schedule(commitJob);
		else
			commitJob.schedule();

