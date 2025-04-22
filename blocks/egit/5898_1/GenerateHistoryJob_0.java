	void release() {
		if (getState() == Job.NONE)
			dispose();
		else
			addJobChangeListener(new JobChangeAdapter() {
				@Override
				public void done(final IJobChangeEvent event) {
					dispose();
				}
			});

	}

	private void dispose() {
		walk.release();
		Display.getDefault().asyncExec(new Runnable() {

			public void run() {
				allCommits.dispose();
			}
		});
	}

