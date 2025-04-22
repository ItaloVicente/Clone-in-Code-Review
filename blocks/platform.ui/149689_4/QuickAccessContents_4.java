		String computingMessage = NLS.bind(QuickAccessMessages.QuickaAcessContents_computeMatchingEntries, filter);
		int maxNumberOfItemsInTable = computeNumberOfItems();
		AtomicReference<List<QuickAccessEntry>[]> entries = new AtomicReference<>();
		final Job currentComputeEntriesJob = Job.create(computingMessage, theMonitor -> {
			entries.set(
					computeMatchingEntries(filter, perfectMatch, extraEntries, maxNumberOfItemsInTable, theMonitor));
			return theMonitor.isCanceled() ? Status.CANCEL_STATUS : Status.OK_STATUS;
		});
		currentComputeEntriesJob.setPriority(Job.INTERACTIVE);
		String feedbackJobName = QuickAccessMessages.QuickAccessContents_computeMatchingEntries_displayFeedback_jobName;
		Job computingFeedbackJob = Job.createSystem(feedbackJobName, montior -> {
			try {
				Thread.sleep(200);
				if (currentComputeEntriesJob.getResult() != null) { // completed
					return;
				}
				table.getDisplay().asyncExec(() -> {
					if (!montior.isCanceled()) {
						showHintText(computingMessage, grayColor);
					}
				});
			} catch (InterruptedException e) {
			}
		});
		currentComputeEntriesJob.addJobChangeListener(new JobChangeAdapter() {
			@Override
			public void done(IJobChangeEvent event) {
				computingFeedbackJob.cancel();
				if (computeProposalsJob == currentComputeEntriesJob && event.getResult().isOK()
						&& !table.isDisposed()) {
					table.getDisplay().asyncExec(() -> refreshTable(perfectMatch, entries.get(), extraEntries, filter));
				}
			}
		});
		this.computeProposalsJob = currentComputeEntriesJob;
		currentComputeEntriesJob.schedule();
		computingFeedbackJob.schedule();
