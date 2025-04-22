		job.addJobChangeListener(new JobChangeAdapter() {

			private final FindToolbarJob myJob = job;

			@Override
			public void done(final IJobChangeEvent event) {
				if (event.getResult().isOK()) {
					getDisplay().asyncExec(new Runnable() {

						@Override
						public void run() {
							if (myJob != job) {
								return;
							}
							if (!isDisposed()) {
								findCompletionUpdate(currentPattern,
										findResults.isOverflow());
							}
						}
					});
				}
			}
		});
		return job;
