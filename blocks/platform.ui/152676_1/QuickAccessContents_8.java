					}
				} else {
					for (Entry<QuickAccessProvider, List<QuickAccessElement>> elementsForProvider : elementsForProviders
							.entrySet()) {
						if (numberOfSlotsLeft > 0) {
							QuickAccessProvider provider = elementsForProvider.getKey();
							List<QuickAccessElement> elements = elementsForProvider.getValue();
							boolean entryPicked = false;
							while (!entryPicked && !elements.isEmpty()) {
								QuickAccessElement element = elements.remove(0);
								QuickAccessEntry entry = new QuickAccessMatcher(element).match(filter, provider);
								if (entry != null) {
									numberOfSlotsLeft--;
									if (!entriesPerProvider.containsKey(provider)) {
										entriesPerProvider.put(provider, new LinkedList<>());
									}
									entriesPerProvider.get(provider).add(entry);
								}
