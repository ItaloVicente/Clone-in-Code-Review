			@Override
			public int compare(ICommonFilterDescriptor o1, ICommonFilterDescriptor o2) {
				return o1.getName().compareTo(o2.getName());
			}
		});

		for (ICommonFilterDescriptor filterDescriptor : filterDescriptors) {
			manager.add(new ToggleFilterAction(commonViewer, filterService, filterDescriptor));
		}
	}

	protected void updateFilterShortcuts(ICommonFilterDescriptor[] filterDescriptorChangeHistory) {
		Deque<ICommonFilterDescriptor> oldestFirstStack = new ArrayDeque<>();
		int length = Math.min(filterDescriptorChangeHistory.length, MAX_FILTER_MENU_ENTRIES);
		for (int i = 0; i < length; i++) {
			oldestFirstStack.push(filterDescriptorChangeHistory[i]);
		}

		length = Math.min(lruFilterDescriptorStack.size(), MAX_FILTER_MENU_ENTRIES - oldestFirstStack.size());
		for (int i = 0; i < length; i++) {
			ICommonFilterDescriptor filter = lruFilterDescriptorStack.pollFirst();
			if (!oldestFirstStack.contains(filter))
				oldestFirstStack.push(filter);
		}
		lruFilterDescriptorStack = oldestFirstStack;
