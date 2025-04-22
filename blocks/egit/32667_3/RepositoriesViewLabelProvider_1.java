		switch (node.getType()) {
		case REPO:
			if (node.getParent() != null
					&& node.getParent().getType() == RepositoryTreeNodeType.SUBMODULES)
				return getStyledTextForSubmodule(node);
			return GitLabels.getStyledLabelExtendedSafe(node.getObject());
		case ADDITIONALREF:
			Ref ref = (Ref) node.getObject();
			StyledString refName = new StyledString(
					Repository.shortenRefName(ref.getName()));
