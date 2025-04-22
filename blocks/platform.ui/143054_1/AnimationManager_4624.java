			}

			private boolean isNotTracked(JobInfo info) {
				Job job = info.getJob();
				return job.getState() != Job.RUNNING
						|| animationProcessor.isProcessorJob(job);
			}

			@Override
