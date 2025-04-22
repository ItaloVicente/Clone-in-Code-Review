						});

						pullJobs[i] = pullJob;
						pullJob.addJobChangeListener(new JobChangeAdapter() {
							@Override
							public void done(
									org.eclipse.core.runtime.jobs.IJobChangeEvent event) {
								progress.worked(1);
