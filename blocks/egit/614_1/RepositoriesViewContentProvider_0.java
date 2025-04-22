			final Collection<File> result = new HashSet<File>();
			final Set<String> traversed = new HashSet<String>();

			try {
				new ProgressMonitorDialog(Display.getDefault().getActiveShell())
						.run(false, false, new IRunnableWithProgress() {

							public void run(IProgressMonitor monitor)
									throws InvocationTargetException,
									InterruptedException {
								collectProjectFilesFromDirectory(result, repo
										.getDirectory().getParentFile(),
										traversed, monitor);

							}
						});
			} catch (InvocationTargetException e) {
				Activator.logError(e.getMessage(), e);
			} catch (InterruptedException e) {
				Activator.logError(e.getMessage(), e);
			}

