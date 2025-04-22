	private QuickAccessMatcher getMatcherFor(QuickAccessElement element) {
		if (!elementsToMatchers.containsKey(element)) {
			elementsToMatchers.put(element, new QuickAccessMatcher(element));
		}
		return elementsToMatchers.get(element);
	}

