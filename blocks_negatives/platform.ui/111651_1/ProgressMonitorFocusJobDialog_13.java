				runAsync(new Runnable() {
					@Override
					public void run() {
						getProgressMonitor().beginTask(finalName, finalWork);
					}
				});
