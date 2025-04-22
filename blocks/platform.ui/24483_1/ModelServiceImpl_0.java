	private <T> void match(Selector matcher, MApplicationElement searchRoot, List<T> elements) {
		if (matcher.select(searchRoot)) {
			if (!elements.contains(searchRoot)) {
				elements.add((T) searchRoot);
			}
		}
	}
