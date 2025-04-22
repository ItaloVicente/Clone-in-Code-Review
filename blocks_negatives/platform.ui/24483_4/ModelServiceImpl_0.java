	private <T> void findElementsRecursive(MApplicationElement searchRoot, Class<T> clazz,
			Selector matcher, List<T> elements, int searchFlags) {
		Assert.isLegal(searchRoot != null);
		if (searchFlags == 0) {
			return;
		}
