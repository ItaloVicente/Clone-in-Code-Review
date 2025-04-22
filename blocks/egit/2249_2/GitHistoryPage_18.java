			pathFilters = null;
			currentWalk.setTreeFilter(TreeFilter.ALL);
			fileWalker.setFilter(TreeFilter.ANY_DIFF);
		}
		return fileWalker;
	}

	private void scheduleNewGenerateHistoryJob() {
		final SWTCommitList list = new SWTCommitList(graph.getControl().getDisplay());
		list.source(currentWalk);
		final GenerateHistoryJob rj = new GenerateHistoryJob(this, list);
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
