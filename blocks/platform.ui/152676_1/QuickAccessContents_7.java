		} else {
			int numberOfSlotsLeft = perfectMatch != null ? maxNumberOfItemsInTable -1 : maxNumberOfItemsInTable;
			while (!elementsForProviders.isEmpty() && numberOfSlotsLeft > 0) {
				int nbEntriesPerProvider = numberOfSlotsLeft / elementsForProviders.size();
				if (nbEntriesPerProvider > 0) {
					for (Entry<QuickAccessProvider, List<QuickAccessElement>> elementsPerProvider : elementsForProviders
							.entrySet()) {
						QuickAccessProvider provider = elementsPerProvider.getKey();
						List<QuickAccessElement> elements = elementsPerProvider.getValue();
						int toPickEntries = nbEntriesPerProvider;
						while (toPickEntries > 0 && !elements.isEmpty()) {
							QuickAccessElement element = elements.remove(0);
							QuickAccessEntry entry = new QuickAccessMatcher(element).match(filter, provider);
							if (entry != null) {
								numberOfSlotsLeft--;
								toPickEntries--;
								if (!entriesPerProvider.containsKey(provider)) {
									entriesPerProvider.put(provider, new LinkedList<>());
								}
								entriesPerProvider.get(provider).add(entry);
