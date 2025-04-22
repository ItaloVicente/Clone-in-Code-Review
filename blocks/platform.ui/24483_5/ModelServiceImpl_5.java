	private <T> void findAllChildren(MApplicationElement searchRoot, Class<T> clazz,
			Selector matcher, List<T> elements, int searchFlags, boolean placeholderLooup) {

		if (clazz != null && clazz.isInstance(searchRoot)) {
			match(matcher, searchRoot, elements);
		} else if (clazz == null) {
			match(matcher, searchRoot, elements);
