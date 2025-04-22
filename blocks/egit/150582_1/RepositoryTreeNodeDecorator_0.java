	private void decorateRepositoryGroup(RepositoryTreeNode<?> node,
			IDecoration decoration) {
		if (((RepositoryGroupNode) node).getGroup().isHidden()) {
			decoration.addSuffix(
					UIText.RepositoriesViewLabelProvider_HiddenRepoGroupText);
		} else {
			decoration.addSuffix("                   ");//$NON-NLS-1$
		}
	}

