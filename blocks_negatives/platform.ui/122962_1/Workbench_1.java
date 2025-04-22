			if (worked) {
				progressMonitor.worked(1);
			}
			if (bundleName != null) {
				String taskName = NLS.bind(WorkbenchMessages.Startup_Loading, bundleName);
				progressMonitor.subTask(taskName);
			}
