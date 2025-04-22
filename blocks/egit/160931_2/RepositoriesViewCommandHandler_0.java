				&& selected.get(0) instanceof RepositoryTreeNode) {
			singleSelection = selected.get(0);
		} else {
			RepositoriesView view = getRepositoriesView();
			if (view != null) {
				StructuredSelection selection = (StructuredSelection) view
						.getCommonViewer().getSelection();
				if (selection != null && selection.size() == 1) {
					singleSelection = selection.getFirstElement();
				}
			}
		}
		if (singleSelection instanceof RepositoryGroupNode) {
			return ((RepositoryGroupNode) singleSelection).getObject();
