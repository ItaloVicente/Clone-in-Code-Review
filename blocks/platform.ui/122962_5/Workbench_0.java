				spinEventQueueToUpdateUi(displayForStartupListener);

			}
			if (eventType == BundleEvent.STARTED) {
				progressMonitor.split(9);
				progressMonitor.subTask(WorkbenchMessages.Startup_Loading_Workbench);

