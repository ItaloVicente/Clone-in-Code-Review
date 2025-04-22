	public static boolean isWorkTreeFilter(ViewerFilter filter) {
		if (filter instanceof NodeByTypeFilter) {
			if (((NodeByTypeFilter) filter).typeToHide == RepositoryTreeNodeType.WORKINGDIR) {
				return true;
			}
		}
		return false;
	}

