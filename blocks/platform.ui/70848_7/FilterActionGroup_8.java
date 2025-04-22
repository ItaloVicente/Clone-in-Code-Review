	protected void updateFilterShortcuts(ICommonFilterDescriptor[] filterDescriptorChangeHistory) {
		Deque<ICommonFilterDescriptor> oldestFirstStack = new ArrayDeque<>();
		int length = Math.min(filterDescriptorChangeHistory.length, MAX_FILTER_MENU_ENTRIES);
		for (int i = 0; i < length; i++) {
			oldestFirstStack.push(filterDescriptorChangeHistory[i]);
		}
