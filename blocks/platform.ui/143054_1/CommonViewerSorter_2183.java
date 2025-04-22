		NavigatorContentServiceContentProvider cp = (NavigatorContentServiceContentProvider) contentService.createCommonContentProvider();
		TreePath[] parentPaths = cp.getParents(element);
		for (TreePath parentPath : parentPaths) {
			if (isSorterProperty(parentPath, element, property))
				return true;
		}
		return false;
	}

	@Override
