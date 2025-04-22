		ICommandService srv = CommonUtils.getService(PlatformUI.getWorkbench(), ICommandService.class);
		verboseBranchModeState = srv.getCommand(ToggleBranchCommitCommand.ID)
				.getState(ToggleBranchCommitCommand.TOGGLE_STATE);
		verboseBranchModeState.addListener(this);
		try {
			this.verboseBranchMode = ((Boolean) verboseBranchModeState
					.getValue()).booleanValue();
		} catch (Exception e) {
			Activator.logError(e.getMessage(), e);
		}
	}

	@Override
	public Image getImage(Object element) {
		RepositoryTreeNode node = (RepositoryTreeNode) element;
		RepositoryTreeNodeType type = node.getType();
		if (type == RepositoryTreeNodeType.TAG) {
			TagNode tagNode = (TagNode) node;
			if (tagNode.isAnnotated())
				return decorateImage(annotatedTagImage, element);
		} else if (type == RepositoryTreeNodeType.FILE) {
			Object object = node.getObject();
			if (object instanceof File) {
				ImageDescriptor descriptor = PlatformUI.getWorkbench()
						.getEditorRegistry()
						.getImageDescriptor(((File) object).getName());
				return decorateImage((Image) resourceManager.get(descriptor),
						element);
			}
		} else if (type == RepositoryTreeNodeType.REPO) {
			Object object = node.getObject();
			if (object instanceof Repository) {
				Repository r = (Repository) object;
				if (ResourcePropertyTester.hasGerritConfiguration(r))
					return gerritRepoImage;
			}
		}
		return decorateImage(node.getType().getIcon(), element);
