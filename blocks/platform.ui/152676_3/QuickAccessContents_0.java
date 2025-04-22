		if (categoryMatcher.matches()) {
			category = categoryMatcher.group(1);
			filter = category + " " + categoryMatcher.group(2); //$NON-NLS-1$
		}
		final String finalFilter = filter;

		@SuppressWarnings("unchecked")
		LinkedHashMap<QuickAccessProvider, List<QuickAccessElement>> elementsForProviders = new LinkedHashMap<>(
				providers.length);
		for (QuickAccessProvider provider : providers) {
			if (aMonitor.isCanceled()) {
				break;
