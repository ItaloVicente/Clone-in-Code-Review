	/**
	 * Returns the table in the shell for testing. Should not be referenced outside
	 * of the tests.
	 *
	 * @return the table created in the shell or <code>null</code>
	 */
	public Table getQuickAccessTable() {
		createContentsAndTable();
		return table;
	}

	/**
	 * Bug 539510: in case of multiple workbench windows we must avoid loading
	 * commands simultaneously for each window
	 */
	private static final ISchedulingRule RESTORE_DIALOG_ENTRIES_SCHEDULING_RULE = new ISchedulingRule() {
		@Override
		public boolean isConflicting(ISchedulingRule rule) {
			return RESTORE_DIALOG_ENTRIES_SCHEDULING_RULE == rule;
		}

		@Override
		public boolean contains(ISchedulingRule rule) {
			return RESTORE_DIALOG_ENTRIES_SCHEDULING_RULE == rule;
		}
	};
