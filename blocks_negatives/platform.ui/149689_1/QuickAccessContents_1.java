	}

	QuickAccessEntry searchHelpEntry = null;
	QuickAccessProvider searchHelpProvider = null;
	QuickAccessHelpSearchElement searchHelpElement = null;

	/**
	 * Instantiate a new {@link QuickAccessEntry} to search the given text in the
	 * eclipse help
	 *
	 * @param text String to search in the Eclipse Help
	 *
	 * @return the {@link QuickAccessEntry} to perform the action
	 */
	private QuickAccessEntry makeHelpSearchEntry(String text) {
		if (searchHelpEntry == null) {
			searchHelpProvider = new QuickAccessHelpSearchProvider();
			searchHelpElement = new QuickAccessHelpSearchElement();
			searchHelpEntry = new QuickAccessEntry(searchHelpElement, searchHelpProvider, new int[][] {},
					new int[][] {}, QuickAccessEntry.MATCH_PERFECT);
			searchHelpEntry.firstInCategory = true;
		}
		searchHelpElement.searchText = text;
		return searchHelpEntry;
	}

	/**
	 * "Search X in help" element shown at the end of the list.
	 */
	static class QuickAccessHelpSearchElement extends QuickAccessElement {

		/** identifier */

		String searchText;

		public QuickAccessHelpSearchElement() {
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
			return WorkbenchImages.getImageDescriptor(IWorkbenchGraphicConstants.IMG_ETOOL_HELP_SEARCH);
		}

	}

	/**
	 * Provider for the "Search X in help" element. Only used to have a category
	 * "Help".
	 */
	static class QuickAccessHelpSearchProvider extends QuickAccessProvider {
		@Override
		public String getName() {
			return QuickAccessMessages.QuickAccessContents_HelpCategory;
		}

		@Override
		public String getId() {
		}

		@Override
		public ImageDescriptor getImageDescriptor() {
			return null;
