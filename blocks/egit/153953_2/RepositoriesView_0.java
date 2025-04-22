
	@SuppressWarnings("restriction")
	private static class PathAddingLabelProvider extends
			org.eclipse.ui.internal.navigator.NavigatorDecoratingLabelProvider {

		public PathAddingLabelProvider(ILabelProvider commonLabelProvider) {
			super(commonLabelProvider);
		}

		@Override
		public void update(ViewerCell cell) {
			RepositoryTreeNodeLabelProvider.update(cell, super::update);
		}
	}
