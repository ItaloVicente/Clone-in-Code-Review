				SWT.NONE, new PatternFilter()) {
			protected WorkbenchJob doCreateRefreshJob() {
				WorkbenchJob refreshJob = super.doCreateRefreshJob();
				refreshJob.addJobChangeListener(new JobChangeAdapter() {
					public void done(IJobChangeEvent event) {
						if (event.getResult().isOK()) {
							getDisplay().asyncExec(new Runnable() {
								public void run() {
									checkPage();
								}
							});
						}
					}
				});
				return refreshJob;
			}
		};
