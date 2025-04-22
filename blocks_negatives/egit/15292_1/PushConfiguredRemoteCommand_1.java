	public void setEnabled(Object evaluationContext) {
		if (evaluationContext instanceof IEvaluationContext) {
			IEvaluationContext ctx = (IEvaluationContext) evaluationContext;
			Object selection = getSelection(ctx);
			if (selection instanceof IStructuredSelection) {
				IStructuredSelection sel = (IStructuredSelection) selection;
				if (sel.getFirstElement() instanceof RepositoryTreeNode) {
					RepositoryTreeNode node = (RepositoryTreeNode) sel.getFirstElement();
					try {
						setBaseEnabled(getRemoteConfig(node) != null);
					} catch (ExecutionException e) {
						Activator.logError(e.getMessage(), e);
						setBaseEnabled(false);
					}

					return;
				}
			}
