			};
			txtQuickAccess.getAccessible().addAccessibleListener(accessibleListener);
		}
	}

	/**
	 * Removes a listener from the
	 * <code>org.eclipse.swt.accessibility.Accessible</code> object assigned to the
	 * Quick Access search box.
	 */
	private void removeAccessibleListener() {
		if (accessibleListener != null) {
			txtQuickAccess.getAccessible().removeAccessibleListener(accessibleListener);
			accessibleListener = null;
		}
	}

	/**
	 * Notifies <code>org.eclipse.swt.accessibility.Accessible</code> object that
	 * selected item has been changed.
	 */
	private void notifyAccessibleTextChanged() {
		if (table.getSelection().length == 0) {
			return;
		}
		TableItem item = table.getSelection()[0];
		selectedString = NLS.bind(QuickAccessMessages.QuickAccess_SelectedString, item.getText(0), item.getText(1));
		txtQuickAccess.getAccessible().sendEvent(ACC.EVENT_NAME_CHANGED, null);
	}

	private void restoreDialog() {
		IDialogSettings dialogSettings = getDialogSettings();
		if (dialogSettings != null) {
			try {
				dialogHeight = dialogSettings.getInt(DIALOG_HEIGHT);
				dialogWidth = dialogSettings.getInt(DIALOG_WIDTH);
			} catch (NumberFormatException e) {
				dialogHeight = -1;
				dialogWidth = -1;
			}

			/*
			 * add place holders, so that we don't change element order due to first
			 * restoring non-UI elements and then restoring UI elements
			 */
			String[] orderedProviders = dialogSettings.getArray(ORDERED_PROVIDERS);
			if (orderedProviders != null) {
				previousPicksList.addAll(Arrays.asList(new QuickAccessElement[orderedProviders.length]));
