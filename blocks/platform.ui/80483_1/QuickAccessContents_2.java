	static class QuickAccessSearchWorkspeceElement extends QuickAccessElement {

		private static final String SEARCH_IN_WORKSPACE_ID = "search.in.workspace"; //$NON-NLS-1$

		String searchText;

		public QuickAccessSearchWorkspeceElement(QuickAccessProvider provider) {
			super(provider);
		}

		@Override
		public String getLabel() {
			return NLS.bind(QuickAccessMessages.QuickAccessContents_SearchInWorkspaceLabel, searchText);
		}

		@Override
		public String getId() {
			return SEARCH_IN_WORKSPACE_ID;
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

