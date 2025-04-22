	private void decorateRepositoryGroup(RepositoryTreeNode<?> node,
			IDecoration decoration) {
		if (((RepositoryGroupNode) node).getGroup().isHideable()) {
			decoration.addSuffix(
					UIText.RepositoriesViewLabelProvider_HideableRepoGroupText);
		} else {
			decoration.addSuffix(" ");//$NON-NLS-1$
		}
	}

