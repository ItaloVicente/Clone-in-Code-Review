	protected void asyncUnlockUI() {
		final Table staged = stagedTableViewer.getTable();
		final Table unstaged = unstagedTableViewer.getTable();
		asyncEnableTable(staged, true);
		asyncEnableTable(unstaged, true);
	}

	protected void asyncLockUI() {
		final Table staged = stagedTableViewer.getTable();
		final Table unstaged = unstagedTableViewer.getTable();
		asyncEnableTable(staged, false);
		asyncEnableTable(unstaged, false);
	}

	private void asyncEnableTable(final Table table, final boolean enable) {
		table.getDisplay().syncExec(new Runnable() {
			public void run() {
				table.setEnabled(enable);
