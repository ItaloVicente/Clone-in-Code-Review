	private void scheduleNewGenerateHistoryJob() {
		final SWTCommitList list = new SWTCommitList(graph.getControl());
		list.source(currentWalk);
		final GenerateHistoryJob rj = new GenerateHistoryJob(this, list);
		rj.setRule(this);
		rj.addJobChangeListener(new JobChangeAdapter() {
			@Override
			public void done(final IJobChangeEvent event) {
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
