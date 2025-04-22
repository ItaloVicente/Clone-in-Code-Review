		IContributionItem item = findUsingPath(path);
		if (item instanceof IMenuManager) {
			return (IMenuManager) item;
		}
		return null;
	}
