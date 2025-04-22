			String taskName;

			if (bundleName == null) {
				taskName = WorkbenchMessages.Startup_Loading_Workbench;
			} else {
				taskName = NLS.bind(WorkbenchMessages.Startup_Loading, bundleName);
