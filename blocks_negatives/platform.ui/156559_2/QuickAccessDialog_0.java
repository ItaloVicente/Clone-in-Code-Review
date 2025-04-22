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
