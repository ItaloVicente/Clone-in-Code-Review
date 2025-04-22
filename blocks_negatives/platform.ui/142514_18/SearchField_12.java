	private void showList() {
		boolean wasVisible = shell != null && shell.getVisible();
		boolean nowVisible = txtQuickAccess.getText().length() > 0 || activated;
		if (!wasVisible && nowVisible) {
			createContentsAndTable();
			layoutShell();
			addAccessibleListener();
			quickAccessContents.preOpen();
		}
		if (wasVisible && !nowVisible) {
			removeAccessibleListener();
		}
		if (nowVisible) {
			notifyAccessibleTextChanged();
		}
		shell.setVisible(nowVisible);
	}

