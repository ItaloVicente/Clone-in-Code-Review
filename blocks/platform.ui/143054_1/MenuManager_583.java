		String id = path;
		String rest = null;
		int separator = path.indexOf('/');
		if (separator != -1) {
			id = path.substring(0, separator);
			rest = path.substring(separator + 1);
		} else {
			return super.find(path);
		}

		IContributionItem item = super.find(id);
		if (item instanceof IMenuManager) {
			IMenuManager manager = (IMenuManager) item;
			return manager.findUsingPath(rest);
		}
		return null;
	}

	private void fireAboutToShow(IMenuManager manager) {
