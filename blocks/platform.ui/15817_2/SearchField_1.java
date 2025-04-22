	private void addAccessibleListener() {
		if (accessibleListener == null) {
			accessibleListener = new AccessibleAdapter() {
				public void getName(AccessibleEvent e) {
					e.result = selectedString;
				}
			};
			text.getAccessible().addAccessibleListener(accessibleListener);
		}
	}

	private void removeAccessibleListener() {
		if (accessibleListener != null) {
			text.getAccessible().removeAccessibleListener(accessibleListener);
			accessibleListener = null;
		}
		selectedString = ""; //$NON-NLS-1$
	}

	private void notifyAccessibleTextChanged() {
		if (table.getSelection().length == 0) {
			return;
		}
		TableItem item = table.getSelection()[0];
		selectedString = NLS.bind(QuickAccessMessages.QuickAccess_SelectedString, item.getText(0),
				item.getText(1));
		text.getAccessible().sendEvent(ACC.EVENT_NAME_CHANGED, null);
	}

