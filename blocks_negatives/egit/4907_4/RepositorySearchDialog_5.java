			};
			try {
				ProgressMonitorDialog pd = new ProgressMonitorDialog(getShell());
				pd
						.getProgressMonitor()
						.setTaskName(
								UIText.RepositorySearchDialog_ScanningForRepositories_message);
				pd.run(true, true, action);

			} catch (InvocationTargetException e1) {
				org.eclipse.egit.ui.Activator.handleError(
						UIText.RepositorySearchDialog_errorOccurred, e1, true);
			} catch (InterruptedException e1) {
