				filesArea, toolkit, SWT.FULL_SELECTION, patternFilter) {
			@Override
			protected WorkbenchJob doCreateRefreshJob() {
				WorkbenchJob filterJob = super.doCreateRefreshJob();
				filterJob.addJobChangeListener(new JobChangeAdapter() {
					public void done(IJobChangeEvent event) {
						if (event.getResult().isOK()) {
							getDisplay().asyncExec(new Runnable() {
								public void run() {
									updateFileSectionText();
								}
							});
						}
					}
				});
				return filterJob;
			}
		};
