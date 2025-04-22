	boolean hasVisibleItems() {
		Pattern filterPattern = getFilterPattern();
		if (filterPattern == null && showUntracked) {
			return getCount() > 0;
		}
		return Stream.of(content)
				.anyMatch(entry -> matches(entry, filterPattern));
	}

