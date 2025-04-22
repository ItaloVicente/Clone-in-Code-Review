		UIJob computingFeedbackJob = new UIJob(table.getDisplay(), QuickAccessMessages.QuickAccessContents_computeMatchingEntries_displayFeedback_jobName) {
			@Override
			public IStatus runInUIThread(IProgressMonitor monitor) {
				if (currentComputeEntriesJob.getResult() == null && !monitor.isCanceled() && !table.isDisposed()) {
					showHintText(computingMessage, grayColor);
					return Status.OK_STATUS;
