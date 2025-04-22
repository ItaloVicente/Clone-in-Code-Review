				if (eventType == BundleEvent.STARTED) {
					progressMonitor.split(4);
					String symbolicName = event.getBundle().getSymbolicName();
					String taskName = NLS.bind(WorkbenchMessages.Startup_Loading, symbolicName);
					progressMonitor.subTask(taskName);

