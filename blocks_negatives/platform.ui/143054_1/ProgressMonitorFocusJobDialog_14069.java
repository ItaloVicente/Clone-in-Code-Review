		BusyIndicator.showWhile(PlatformUI.getWorkbench().getDisplay(),
				() -> {
					try {
						synchronized (jobIsDone) {
							if (job.getState() != Job.NONE) {
								jobIsDone.wait(ProgressManagerUtil.SHORT_OPERATION_TIME);
							}
						}
					} catch (InterruptedException e) {
