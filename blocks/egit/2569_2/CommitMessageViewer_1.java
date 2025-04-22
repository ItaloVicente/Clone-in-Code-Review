	void addDoneListenerToFormatJob() {
		formatJob.addJobChangeListener(new IJobChangeListener() {

			public void sleeping(IJobChangeEvent event) {
			}

			public void scheduled(IJobChangeEvent event) {
			}

			public void running(IJobChangeEvent event) {
			}

			public void done(IJobChangeEvent event) {
				if (event.getResult().isOK()) {
					if (getTextWidget().isDisposed())
						return;
					final FormatJob job = (FormatJob) event.getJob();
					getTextWidget().getDisplay().asyncExec(new Runnable() {
						public void run() {
							setDocument(new Document(job.getFormatResult().getCommitInfo()));
							getTextWidget().setStyleRanges(job.getFormatResult().getStyleRange());
						}
					});
				}
			}

			public void awake(IJobChangeEvent event) {
			}

			public void aboutToRun(IJobChangeEvent event) {
			}
		});
	}


