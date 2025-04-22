		if (dialogSettings == null) {
			return;
		}
		String[] orderedElements = dialogSettings.getArray(ORDERED_ELEMENTS);
		String[] orderedProviders = dialogSettings.getArray(ORDERED_PROVIDERS);
		String[] textEntries = dialogSettings.getArray(TEXT_ENTRIES);
		String[] textArray = dialogSettings.getArray(TEXT_ARRAY);
		elementMap = new HashMap<>();
		textMap = new HashMap<>();
		if (orderedElements != null && orderedProviders != null && textEntries != null && textArray != null) {
			int arrayIndex = 0;
			for (int i = 0; i < orderedElements.length; i++) {
				QuickAccessProvider quickAccessProvider = providerMap.get(orderedProviders[i]);
				int numTexts = Integer.parseInt(textEntries[i]);
				if (quickAccessProvider != null) {
					QuickAccessElement quickAccessElement = quickAccessProvider.getElementForId(orderedElements[i]);
					if (quickAccessElement != null) {
						contents.registerProviderFor(quickAccessElement, quickAccessProvider);
						ArrayList<String> arrayList = new ArrayList<>();
						for (int j = arrayIndex; j < arrayIndex + numTexts; j++) {
							String text = textArray[j];
							if (!text.isEmpty()) {
								arrayList.add(text);
								elementMap.put(text, quickAccessElement);
