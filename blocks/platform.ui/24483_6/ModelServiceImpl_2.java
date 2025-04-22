	@Override
	public <T> List<T> findElements(MApplicationElement searchRoot, Class<T> clazz,
			int searchFlags, Selector matcher) {
		Assert.isLegal(searchRoot != null & matcher != null);
		
		List<T> elements = new ArrayList<T>();
