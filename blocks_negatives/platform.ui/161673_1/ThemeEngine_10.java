	@Override
	public synchronized void registerResourceLocator(IResourceLocator locator,
			String... themes) {
		if (themes.length == 0) {
			globalSourceLocators.add(locator);
		} else {
			for (String t : themes) {
				List<IResourceLocator> list = sourceLocators.get(t);
				if (list == null) {
					list = new ArrayList<>();
					sourceLocators.put(t, list);
