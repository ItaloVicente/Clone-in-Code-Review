			if (runInBackground) {
				progressDialog.run(runInBackground, true, runnable);
			} else {
				final IJobManager manager = Job.getJobManager();
				final ISchedulingRule rule = ResourcesPlugin.getWorkspace().getRoot(); 
				try {
					try {
						Runnable r = () -> manager.beginRule(rule, null);
						BusyIndicator.showWhile(parent.getDisplay(), r);
					} catch (OperationCanceledException e) {
						throw new InterruptedException(e.getMessage());
					}
					progressDialog.run(runInBackground, true, runnable); // <-- actual work
				} finally {
					manager.endRule(rule);
				}
			}
