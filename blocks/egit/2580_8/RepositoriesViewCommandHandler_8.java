
	protected void enableWhenRepositoryHaveHead(Object evaluationContext) {
		if (evaluationContext instanceof EvaluationContext) {
			EvaluationContext context = (EvaluationContext) evaluationContext;
			Object selection = context.getVariable(ISources.ACTIVE_CURRENT_SELECTION_NAME);
			if (selection instanceof TreeSelection) {
				TreeSelection treeSelection = (TreeSelection) selection;
				Object firstElement = treeSelection.getFirstElement();
				if (firstElement instanceof RepositoryTreeNode) {
					RepositoryTreeNode<?> treeNode = (RepositoryTreeNode<?>) firstElement;
					Repository repo = treeNode.getRepository();
					boolean enabled = false;
					try {
						enabled = repo.getRef(Constants.HEAD).getObjectId() != null;
					} catch (IOException e) {
						enabled = false;
					}
					setBaseEnabled(enabled);
					return;
				}
			}
		}

		setBaseEnabled(false);
	}
