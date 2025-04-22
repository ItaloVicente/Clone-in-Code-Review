		statusListViewer.addPostSelectionChangedListener(new ISelectionChangedListener() {
			@Override
			public void selectionChanged(SelectionChangedEvent event) {
				handleSelectionChange();
				if ((getTray() == null) && getBooleanValue(IStatusDialogConstants.TRAY_OPENED)
						&& providesSupport()) {
					silentTrayOpen();
					return;
				}
				if ((getTray() != null) && !providesSupport()) {
					silentTrayClose();
					return;
				}
				supportTray.selectionChanged(event);
