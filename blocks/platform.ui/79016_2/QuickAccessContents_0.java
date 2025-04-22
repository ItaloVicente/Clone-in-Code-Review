	QuickAccessEntry searchHelpEntry = null;
	QuickAccessProvider searchHelpProvider = null;
	QuickAccessSearchElement searchHelpElement = null;

	private void addHelpSearch(String text) {
		table.removeAll();
		TableItem item = new TableItem(table, SWT.NONE);
		if (searchHelpEntry == null) {
			searchHelpProvider = Stream.of(providers).filter(p -> p instanceof ActionProvider).findFirst().get();
			searchHelpElement = new QuickAccessSearchElement(searchHelpProvider);
			searchHelpEntry = new QuickAccessEntry(searchHelpElement, searchHelpProvider, new int[][] {},
					new int[][] {}, QuickAccessEntry.MATCH_PERFECT);
		}
		searchHelpElement.searchText = text;
		item.setData(searchHelpEntry);
		table.setSelection(0);
	}

	static class QuickAccessSearchElement extends QuickAccessElement {

		String searchText;

		public QuickAccessSearchElement(QuickAccessProvider provider) {
			super(provider);
		}

		@Override
		public String getLabel() {
			return QuickAccessMessages.QuickAccessContents_SearchInHelp;
		}

		@Override
		public String getId() {
			return "search.in.help"; //$NON-NLS-1$
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

