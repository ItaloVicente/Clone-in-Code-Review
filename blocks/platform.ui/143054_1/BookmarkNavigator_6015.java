				}
				updateSortState();
				viewer.refresh();
				IDialogSettings workbenchSettings = getPlugin()
						.getDialogSettings();
				IDialogSettings settings = workbenchSettings
						.getSection("BookmarkSortState");//$NON-NLS-1$
				if (settings == null) {
