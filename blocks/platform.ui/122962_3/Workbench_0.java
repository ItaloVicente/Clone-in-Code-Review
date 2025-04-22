				spinEventQueueToUpdateUi(displayForStartupListener);

			}
			if (eventType == BundleEvent.STARTED) {
				progressMonitor.split(5);
				progressMonitor.subTask(WorkbenchMessages.Startup_Loading_Workbench);

