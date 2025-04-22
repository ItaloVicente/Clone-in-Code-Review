		String id = path;
		String rest = null;
		int separator = path.indexOf('/');
		if (separator != -1) {
			id = path.substring(0, separator);
			rest = path.substring(separator + 1);
		}
		IContributionItem item = find(id); // unwraps item
		if (rest != null && item instanceof IMenuManager) {
			IMenuManager menu = (IMenuManager) item;
			item = menu.findUsingPath(rest);
		}
		return item;
	}

	@Override
