		Object workbenchListener = new Object() {
			@Inject
			@PostSave
			public void modelSaved(Logger logger) {
				createDirectoryHierarchy(newDataLocation);
				try {
					copyFile(currentDataLocation.toOSString(), newDataLocation.toOSString(), DELTAS_FILE);
					copyFile(currentDataLocation.toOSString(), newDataLocation.toOSString(), WORKBENCH_FILE);
				} catch (IOException e) {
					logger.error(e, WorkbenchMessages.Workbench_problemsSavingMsg);
