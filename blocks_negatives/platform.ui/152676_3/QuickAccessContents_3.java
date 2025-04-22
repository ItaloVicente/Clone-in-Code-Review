					QuickAccessElement[] sortedElements = sortedElementRef.get();
					if (sortedElements == null) {
						sortedElements = new QuickAccessElement[0];
					}
					sortedElements = Arrays.copyOf(sortedElements, sortedElements.length);
					if (!(provider instanceof PreviousPicksProvider)) {
						for (QuickAccessElement element : sortedElements) {
							elementsToProviders.put(element, provider);
						}
