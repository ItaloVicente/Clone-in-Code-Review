	private Image getRefIcon(Ref ref) {
		String name = ref.getName();
		if (name.startsWith(Constants.R_HEADS)
				|| name.startsWith(Constants.R_REMOTES))
			return RepositoryTreeNodeType.REF.getIcon();
		else if (name.startsWith(Constants.R_TAGS))
			return RepositoryTreeNodeType.TAG.getIcon();
		else
			return RepositoryTreeNodeType.ADDITIONALREF.getIcon();
	}

