			IJobRunnable result = new IJobRunnable() {
				@Override
				public IStatus run(IProgressMonitor monitor) {
					monitor.beginTask("Saving in the background",
							data.backgroundSaveTime);
					for (int i = 0; i < data.backgroundSaveTime; i++) {
						if (monitor.isCanceled()) {
							return Status.CANCEL_STATUS;
						}
						try {
							Thread.sleep(1000);
						} catch (InterruptedException e) {
							Thread.currentThread().interrupt();
						}
						data.setOutput(data.getInput().substring(
								0,
								Math.min(i + data.foregroundSaveTime, data
										.getInput().length())));
						monitor.worked(1);
