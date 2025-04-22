		final TagsNode tagsNode = new TagsNode(null, repository);
		RepositoriesViewContentProvider contentProvider = new RepositoriesViewContentProvider() {
			@Override
			public Object[] getElements(Object inputElement) {
				return getChildren(tagsNode);
			}
		};
