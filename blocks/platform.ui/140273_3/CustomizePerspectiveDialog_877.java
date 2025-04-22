		menuItemsViewer.setContentProvider(new TreeManager.TreeItemContentProvider() {
			@Override
			public Object[] getChildren(Object parentElement) {
				if (parentElement instanceof Category) {
					return ((Category) parentElement).getContributionItems().toArray();
				}
				return super.getChildren(parentElement);
			}
		});
