
	@Override
	public void setEnabled(Object evaluationContext) {
		if (evaluationContext instanceof IEvaluationContext) {
			IEvaluationContext ctx = (IEvaluationContext) evaluationContext;
			Object selection = ctx
					.getVariable(ISources.ACTIVE_MENU_SELECTION_NAME);
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
		}

		setBaseEnabled(false);
	}

	private RemoteConfig getRemoteConfig(RepositoryTreeNode node)
			throws ExecutionException {
		if (node instanceof FetchNode)
			try {
				RemoteNode remote = (RemoteNode) node.getParent();
				return  new RemoteConfig(node.getRepository().getConfig(),
						remote.getObject());
			} catch (URISyntaxException e) {
				throw new ExecutionException(e.getMessage());
			}

		if (node instanceof RepositoryNode)
			return SimpleConfigureFetchDialog.getConfiguredRemote(node
					.getRepository());

		return null;
	}
