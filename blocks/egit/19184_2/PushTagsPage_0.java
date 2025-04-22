
	private static class ContentProvider extends
			RepositoriesViewContentProvider {
		private final Object[] children;

		private ContentProvider(TagsNode tagsNode) {
			this.children = getChildren(tagsNode);
		}

		@Override
		public Object[] getElements(Object inputElement) {
			return children;
		}
	}
