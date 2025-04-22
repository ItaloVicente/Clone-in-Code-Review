				StatusManager
						.getManager()
						.handle(
								new Status(
										IStatus.ERROR,
										PlatformUI.PLUGIN_ID,
										IStatus.ERROR,
										WorkbenchMessages.FilteredItemsSelectionDialog_restoreError,
										e));
