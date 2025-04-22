				|| name.startsWith(Constants.R_REMOTES)) {
			return UIIcons.getImage(getImageCache(),
					RepositoryTreeNodeType.REF.getIcon());
		} else if (name.startsWith(Constants.R_TAGS)) {
			return UIIcons.getImage(getImageCache(),
					RepositoryTreeNodeType.TAG.getIcon());
		} else {
			return UIIcons.getImage(getImageCache(),
					RepositoryTreeNodeType.ADDITIONALREF.getIcon());
		}
