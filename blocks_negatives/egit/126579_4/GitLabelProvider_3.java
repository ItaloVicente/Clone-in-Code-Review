				|| name.startsWith(Constants.R_REMOTES))
			return RepositoryTreeNodeType.REF.getIcon();
		else if (name.startsWith(Constants.R_TAGS))
			return RepositoryTreeNodeType.TAG.getIcon();
		else
			return RepositoryTreeNodeType.ADDITIONALREF.getIcon();
