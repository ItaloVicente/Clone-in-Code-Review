	@Override
	protected String refNameFromDialog() {
		selectedRefs.clear();
		Set<String> selected = new HashSet<String>();
		IStructuredSelection selection = (IStructuredSelection) branchTree.getSelection();
		for (Object sel : selection.toArray()) {
			RepositoryTreeNode node = (RepositoryTreeNode) sel;
			if (node.getType() == RepositoryTreeNodeType.REF
					|| node.getType() == RepositoryTreeNodeType.TAG
					|| node.getType() == RepositoryTreeNodeType.ADDITIONALREF) {
				Ref ref = (Ref) node.getObject();
				selectedRefs.add(ref);
				selected.add(ref.getName());
			}
		}
		getButton(Window.OK).setEnabled(!selected.contains(currentBranch));

		return null;
	}

