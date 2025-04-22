		length = Math.min(lruFilterDescriptorStack.size(), MAX_FILTER_MENU_ENTRIES - oldestFirstStack.size());
		for (int i = 0; i < length; i++) {
			ICommonFilterDescriptor filter = lruFilterDescriptorStack.pollFirst();
			if (!oldestFirstStack.contains(filter))
				oldestFirstStack.push(filter);
		}
		lruFilterDescriptorStack = oldestFirstStack;
