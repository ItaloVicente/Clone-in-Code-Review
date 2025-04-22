				runAsync(new Runnable() {
					@Override
					public void run() {
						getProgressMonitor().internalWorked(finalWork);
					}
				});
