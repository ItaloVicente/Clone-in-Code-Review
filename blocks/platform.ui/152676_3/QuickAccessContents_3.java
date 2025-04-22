				} else {
					sortedElementRef.set(Arrays.asList(provider.getElementsSorted(filter, aMonitor)));
				}
				List<QuickAccessElement> sortedElements = sortedElementRef.get();
				if (sortedElements == null) {
					sortedElements = Collections.emptyList();
				}
				if (!(provider instanceof PreviousPicksProvider)) {
					for (QuickAccessElement element : sortedElements) {
						elementsToProviders.put(element, provider);
