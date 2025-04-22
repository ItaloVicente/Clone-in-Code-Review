	private QuickAccessEntry makeSearchWorkspaceEntry(String text) {
		if (searchWorkspaceEntry == null) {
			searchWorkspaceElement = new QuickAccessSearchWorkspeceElement(getSearchProvider());
			searchWorkspaceEntry = new QuickAccessEntry(searchWorkspaceElement, getSearchProvider(), new int[][] {},
					new int[][] {}, QuickAccessEntry.MATCH_PERFECT);
		}
		searchWorkspaceElement.searchText = text;
		return searchWorkspaceEntry;
	}

	static class QuickAccessSearchHelpElement extends QuickAccessElement {
