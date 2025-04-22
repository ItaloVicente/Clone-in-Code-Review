		commitJob.addJobChangeListener(new JobChangeAdapter() {
			@Override
			public void done(IJobChangeEvent event) {
				commitPending = false;
				asyncExec(new Runnable() {
					public void run() {
						firePropertyChange(PROP_DIRTY);
					}
				});
			}
		});
