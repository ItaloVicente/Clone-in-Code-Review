	QuickAccessEntry searchWorkspaceEntry = null;
	QuickAccessSearchHelpElement searchHelpElement = null;
	QuickAccessSearchWorkspeceElement searchWorkspaceElement = null;

	public QuickAccessProvider getSearchProvider() {
		if (searchProvider == null) {
			searchProvider = Stream.of(providers).filter(p -> p instanceof ActionProvider).findFirst().get();
		}
		return searchProvider;
	}
