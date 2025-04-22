		statusListViewer.addPostSelectionChangedListener(event -> {
			handleSelectionChange();
			if ((getTray() == null) && getBooleanValue(IStatusDialogConstants.TRAY_OPENED)
					&& providesSupport()) {
				silentTrayOpen();
				return;
			}
			if ((getTray() != null) && !providesSupport()) {
				silentTrayClose();
				return;
