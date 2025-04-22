		for (int i = 0; i < stores.length; i++) {
			if (stores[i].fetchInfo().exists() == false) {
				String message = NLS
						.bind(
								IDEWorkbenchMessages.CopyFilesAndFoldersOperation_resourceDeleted,
								stores[i].getName());
				IStatus status = new Status(IStatus.ERROR,
						PlatformUI.PLUGIN_ID, IStatus.OK, message, null);
