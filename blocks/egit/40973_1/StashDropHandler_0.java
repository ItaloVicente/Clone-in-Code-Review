		final IWorkbenchPart part = getPart(event);
		job.addJobChangeListener(new JobChangeAdapter() {
			@Override
			public void done(IJobChangeEvent event) {
				if (event.getResult().isOK()) {
					if (part instanceof CommitEditor) {
						((CommitEditor) part).close(false);
					}
				}
			}

		});
