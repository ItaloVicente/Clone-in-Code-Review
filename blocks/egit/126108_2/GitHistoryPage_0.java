	private void showSelection(boolean showHead, boolean showRef,
			boolean showTag,
			Repository repo, RevCommit selection, Ref ref, Job initJob) {
		if (initJob == null) {
			return;
		}
		initJob.addJobChangeListener(new JobChangeAdapter() {
			@Override
			public void done(IJobChangeEvent event) {
				if (event.getResult() == null || !event.getResult().isOK()
						|| job != event.getJob()) {
					return;
				}
				Control control = graph.getControl();
				if (control.isDisposed()) {
					return;
				}
				control.getDisplay().asyncExec(() -> {
					if (control.isDisposed()) {
						return;
					}
					if (showHead) {
						showHead(repo);
					}
					if (showRef) {
						showRef(ref, repo);
					}
					if (showTag) {
						showTag(ref, repo);
					}
					if (selection != null) {
						graph.selectCommitStored(selection);
					}
				});
			}
		});
	}

