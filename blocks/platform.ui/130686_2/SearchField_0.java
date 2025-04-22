
	private static ISchedulingRule RESTORE_DIALOG_ENTRIES_SCHEDULING_RULE = new ISchedulingRule() {
		@Override
		public boolean isConflicting(ISchedulingRule rule) {
			return RESTORE_DIALOG_ENTRIES_SCHEDULING_RULE == rule;
		}

		@Override
		public boolean contains(ISchedulingRule rule) {
			return RESTORE_DIALOG_ENTRIES_SCHEDULING_RULE == rule;
		}
	};
