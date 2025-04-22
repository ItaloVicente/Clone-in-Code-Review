			StatusManager
					.getManager()
					.handle(
							new Status(
									IStatus.ERROR,
									PlatformUI.PLUGIN_ID,
									IStatus.ERROR,
									WorkbenchMessages.FilteredItemsSelectionDialog_storeError,
									e));
