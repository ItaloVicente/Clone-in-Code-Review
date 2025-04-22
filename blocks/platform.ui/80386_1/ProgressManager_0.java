				final Job job = event.getJob();
				JobInfo info = getOrCreateJobInfo(job);
				display.asyncExec(() -> {
					if (shouldDisplay(info.getJob(), showSystemJobs)) {
						for (IJobProgressManagerListener listener : listeners) {
							listener.addJob(info);
						}
					}
				});

				if (job.isUser()) {
					if (!shouldRunInBackground()) {
						WorkbenchJob showJob = new WorkbenchJob(ProgressMessages.ProgressManager_showInDialogName) {
