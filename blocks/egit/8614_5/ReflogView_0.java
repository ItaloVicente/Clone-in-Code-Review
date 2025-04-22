	public boolean show(ShowInContext context) {
		ISelection selection = context.getSelection();
		if (selection instanceof IStructuredSelection) {
			IStructuredSelection structuredSelection = (IStructuredSelection) selection;
			for (Object element : structuredSelection.toList()) {
				if (element instanceof RepositoryTreeNode) {
					RepositoryTreeNode node = (RepositoryTreeNode) element;
					showReflogFor(node.getRepository());
					return true;
				}
			}
		}
		return false;
	}

