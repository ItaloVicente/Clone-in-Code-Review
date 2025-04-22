		Object selection = HandlerUtil.getVariable(evaluationContext,
				ISources.ACTIVE_CURRENT_SELECTION_NAME);
		if (selection instanceof TreeSelection) {
			TreeSelection treeSelection = (TreeSelection) selection;
			Object firstElement = treeSelection.getFirstElement();
			if (firstElement instanceof RepositoryTreeNode) {
				RepositoryTreeNode<?> treeNode = (RepositoryTreeNode<?>) firstElement;
				Repository repo = treeNode.getRepository();
				boolean enabled = false;
				try {
					Ref ref = repo.getRef(Constants.HEAD);
					enabled = ref != null && ref.getObjectId() != null;
				} catch (IOException e) {
					enabled = false;
