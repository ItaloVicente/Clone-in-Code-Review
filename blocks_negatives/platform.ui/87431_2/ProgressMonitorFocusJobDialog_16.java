				new Runnable() {
					@Override
					public void run() {
						try {
							synchronized (jobIsDone) {
								if (job.getState() != Job.NONE) {
									jobIsDone.wait(ProgressManagerUtil.SHORT_OPERATION_TIME);
								}
