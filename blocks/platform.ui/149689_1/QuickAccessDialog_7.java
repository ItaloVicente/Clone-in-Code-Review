		return res;
	}

	private Collection<String> getPreviousPickProviderIds(IDialogSettings dialogSettings) {
		if (dialogSettings == null) {
			return Collections.emptySet();
		}
		String[] orderedProviders = dialogSettings.getArray(ORDERED_PROVIDERS);
		if (orderedProviders == null) {
			return Collections.emptySet();
		}
		return new HashSet<>(Arrays.asList(orderedProviders));
