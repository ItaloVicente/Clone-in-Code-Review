			return WorkbenchImages.getImageDescriptor(IWorkbenchGraphicConstants.IMG_ETOOL_HELP_SEARCH);
		}

	}

	static class QuickAccessHelpSearchProvider extends QuickAccessProvider {
		@Override
		public String getName() {
			return QuickAccessMessages.QuickAccessContents_HelpCategory;
		}

		@Override
		public String getId() {
			return "search.help"; //$NON-NLS-1$
		}

		@Override
		public ImageDescriptor getImageDescriptor() {
			return null;
		}

		@Override
		public QuickAccessElement[] getElements() {
