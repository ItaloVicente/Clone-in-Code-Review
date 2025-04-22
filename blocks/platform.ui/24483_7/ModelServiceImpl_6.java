	private <T> void match(Selector matcher, Object searchRoot, List<T> elements) {
		if (searchRoot instanceof MApplicationElement) {
			if (matcher.select((MApplicationElement) searchRoot)) {
				if (!elements.contains(searchRoot)) {
					elements.add((T) searchRoot);
				}
			}
		}
