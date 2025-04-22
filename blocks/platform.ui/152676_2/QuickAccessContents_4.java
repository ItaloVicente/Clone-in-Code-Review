				}
				if (!filter.isEmpty() && !sortedElements.isEmpty()) {
					sortedElements = putPrefixMatchFirst(sortedElements, filter);
				}
				elementsForProviders.put(provider, new ArrayList<>(sortedElements));
			}
		}
