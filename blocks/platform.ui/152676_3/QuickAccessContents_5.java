		List<String> prevPickIds = new ArrayList<>();
		for (Entry<QuickAccessProvider, List<QuickAccessElement>> entry : elementsForProviders.entrySet()) {
			if (entry.getKey() instanceof PreviousPicksProvider) {
				prevPickIds
						.addAll(entry.getValue().stream().map(QuickAccessElement::getId).collect(Collectors.toList()));
			}
		}
		for (Entry<QuickAccessProvider, List<QuickAccessElement>> entry : elementsForProviders.entrySet()) {
			if (!(entry.getKey() instanceof PreviousPicksProvider)) {
				List<QuickAccessElement> filteredElements = new ArrayList<>(entry.getValue());
				filteredElements.removeIf(element -> prevPickIds.contains(element.getId()));
				entry.setValue(filteredElements);
			}
		}
		QuickAccessProvider perfectMatchProvider = null;
		if (perfectMatch != null) {
			for (Entry<QuickAccessProvider, List<QuickAccessElement>> entry : elementsForProviders.entrySet()) {
				if (perfectMatchProvider != null) {
					List<QuickAccessElement> filteredElements = new ArrayList<>(entry.getValue());
					if (filteredElements.removeIf(element -> prevPickIds.contains(element.getId()))) {
						entry.setValue(filteredElements);
						perfectMatchProvider = entry.getKey();
