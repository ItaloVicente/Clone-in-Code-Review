				IRunnableWithProgress r = new IRunnableWithProgress() {
					@Override
					public void run(IProgressMonitor monitor) {
						monitor.beginTask(Messages.searchingTaskName,
								IProgressMonitor.UNKNOWN);
						search(rootDir, existingPaths, foundBrowsers, new HashSet<String>(), monitor);
						monitor.done();
					}
