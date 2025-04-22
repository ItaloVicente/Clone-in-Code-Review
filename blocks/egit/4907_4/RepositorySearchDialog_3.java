			public void run(IProgressMonitor monitor)
					throws InvocationTargetException, InterruptedException {
				monitor.beginTask(
						UIText.RepositorySearchDialog_ScanningForRepositories_message,
						IProgressMonitor.UNKNOWN);
				try {
					findGitDirsRecursive(file, directories, monitor,
							lookForNested);
				} catch (Exception ex) {
					Activator
							.getDefault()
							.getLog()
							.log(new Status(IStatus.ERROR, Activator
									.getPluginId(), ex.getMessage(), ex));
				}
				if (monitor.isCanceled()) {
					throw new InterruptedException();
