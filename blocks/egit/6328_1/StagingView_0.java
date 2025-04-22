	protected void syncUnlockUI() {
		final Table staged = stagedTableViewer.getTable();
		final Table unstaged = unstagedTableViewer.getTable();
		syncEnableTable(staged, true);
		syncEnableTable(unstaged, true);
	}

	protected void syncLockUI() {
		final Table staged = stagedTableViewer.getTable();
		final Table unstaged = unstagedTableViewer.getTable();
		syncEnableTable(staged, false);
		syncEnableTable(unstaged, false);
	}

	private void syncEnableTable(final Table table, final boolean enable) {
		table.getDisplay().syncExec(new Runnable() {
			public void run() {
				table.setEnabled(enable);
