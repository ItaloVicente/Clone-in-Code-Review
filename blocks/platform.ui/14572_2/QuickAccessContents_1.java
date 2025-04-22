	private boolean entryEnabled(QuickAccessProvider provider, QuickAccessEntry entry) {
		if (entry == null) {
			return false;
		}

		if (provider instanceof PreviousPicksProvider) {
			QuickAccessElement element = entry.element;
			final QuickAccessProvider originalProvider = element.getProvider();
			QuickAccessElement match = originalProvider.getElementForId(element.getId());
			return match != null;
		}

		return true;
	}

