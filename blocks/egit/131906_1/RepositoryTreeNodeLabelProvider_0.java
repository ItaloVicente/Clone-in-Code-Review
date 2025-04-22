
	@Override
	public void restoreState(IMemento memento) {
	}

	@Override
	public void saveState(IMemento memento) {
	}

	@Override
	public String getDescription(Object element) {
		StringBuilder result = new StringBuilder(getText(element));
		if (element instanceof RepositoryTreeNode) {
			if (((RepositoryTreeNode) element)
					.getType() != RepositoryTreeNodeType.REPO) {
				Repository repo = ((RepositoryTreeNode) element)
						.getRepository();
				result.append(" - ").append(GitLabels.getPlainShortLabel(repo)); //$NON-NLS-1$
			}
		}
		return result.toString();
	}

	@Override
	public void init(ICommonContentExtensionSite config) {
	}
