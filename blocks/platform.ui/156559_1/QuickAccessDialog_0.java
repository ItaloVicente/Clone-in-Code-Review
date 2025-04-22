		if (previousPicksProvider.elements != null) {
			String[] orderedElements = new String[previousPicksProvider.elements.size()];
			String[] orderedProviders = new String[previousPicksProvider.elements.size()];
			String[] textEntries = new String[previousPicksProvider.elements.size()];
			ArrayList<String> arrayList = new ArrayList<>();
			for (int i = 0; i < orderedElements.length; i++) {
				QuickAccessElement quickAccessElement = previousPicksProvider.elements.get(i);
				ArrayList<String> elementText = textMap.get(quickAccessElement);
				Assert.isNotNull(elementText);
				orderedElements[i] = quickAccessElement.getId();
				orderedProviders[i] = contents.getProviderFor(quickAccessElement).getId();
				arrayList.addAll(elementText);
				textEntries[i] = elementText.size() + ""; //$NON-NLS-1$
			}
			String[] textArray = arrayList.toArray(new String[arrayList.size()]);
			dialogSettings.put(ORDERED_ELEMENTS, orderedElements);
			dialogSettings.put(ORDERED_PROVIDERS, orderedProviders);
			dialogSettings.put(TEXT_ENTRIES, textEntries);
			dialogSettings.put(USER_INPUT_TEXTS, textArray);
