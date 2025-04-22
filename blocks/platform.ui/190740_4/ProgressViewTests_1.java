			progressView.getViewer().refresh(); // order will only hold on the first time.
			Thread.sleep(200); // wait till throttled update ran.
			Job dummyJob = new Job("dummy throttled caller") {
				@Override
				protected IStatus run(IProgressMonitor monitor) {
					return Status.OK_STATUS;
				}
			};
			dummyJob.schedule(); // trigger throttled update to clear ProgressViewerComparator.lastIndexes
