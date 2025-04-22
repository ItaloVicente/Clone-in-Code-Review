		if (needsRecompute) {
			needsRecompute = false;
			FileDiffInput input = currentInput;
			if (input != null) {
				cancel();
				FileDiffLoader job = new FileDiffLoader(input, markTreeFilter);
				job.addJobChangeListener(new JobChangeAdapter() {
					@Override
					public void done(IJobChangeEvent event) {
						if (!event.getResult().isOK()) {
							return;
						}
						UIJob updater = new UIJob(MessageFormat.format(
								UIText.FileDiffContentProvider_updatingFileDiffs,
								input.getCommit().getName())) {

							@Override
							public IStatus runInUIThread(
									IProgressMonitor monitor) {
								if (!viewer.getControl().isDisposed()
										&& loader == job) {
									diff = job.getDiffs();
									viewer.refresh();
									FileDiff interesting = getFirstInterestingElement();
									if (interesting != null) {
										if (input.isSelectMarked()) {
											viewer.setSelection(
													new StructuredSelection(
															interesting),
													true);
										} else {
											viewer.reveal(interesting);
										}
									}
								}
								return Status.OK_STATUS;
							}
						};
						updater.schedule();
					}
				});
				job.setUser(false);
				job.setSystem(true);
				loader = job;
				loader.schedule();
			} else {
				diff = null;
