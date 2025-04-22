	private void scheduleNewGenerateHistoryJob(RevWalk walk) {
		final GenerateHistoryJob rj = new GenerateHistoryJob(this,
				graph.getControl(), walk);
		rj.setRule(this);
		rj.addJobChangeListener(new JobChangeAdapter() {
			@Override
			public void done(final IJobChangeEvent event) {
				rj.getWalk().release();
				final Control graphctl = graph.getControl();
				if (job != rj || graphctl.isDisposed())
					return;
				graphctl.getDisplay().asyncExec(new Runnable() {
					public void run() {
						if (job == rj)
							job = null;
					}
				});
			}
		});
		job = rj;
