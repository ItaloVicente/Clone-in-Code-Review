	QuickAccessEntry searchHelpEntry = null;
	QuickAccessProvider searchHelpProvider = null;
	QuickAccessSearchElement searchHelpElement = null;

	private QuickAccessEntry makeHelpSearchEntry(String text) {
		if (searchHelpEntry == null) {
			searchHelpProvider = Stream.of(providers).filter(p -> p instanceof ActionProvider).findFirst().get();
			searchHelpElement = new QuickAccessSearchElement(searchHelpProvider);
			searchHelpEntry = new QuickAccessEntry(searchHelpElement, searchHelpProvider, new int[][] {},
					new int[][] {}, QuickAccessEntry.MATCH_PERFECT);
		}
		searchHelpElement.searchText = text;
		return searchHelpEntry;
	}

	static class QuickAccessSearchElement extends QuickAccessElement {

		private static final String SEARCH_IN_HELP_ID = "search.in.help"; //$NON-NLS-1$

		String searchText;

		public QuickAccessSearchElement(QuickAccessProvider provider) {
			super(provider);
		}

		@Override
		public String getLabel() {
			return NLS.bind(QuickAccessMessages.QuickAccessContents_SearchInHelpLabel, searchText);
		}

		@Override
		public String getId() {
			return SEARCH_IN_HELP_ID;
		}

		@Override
		public void execute() {
			PlatformUI.getWorkbench().getHelpSystem().search(searchText);
		}

		@Override
		public ImageDescriptor getImageDescriptor() {
			return null;
		}

	}

