			children.put(parent, childList);
		}

		childList.add(child);
	}

	protected void createContainer(IPath pathname) {
		if (directoryEntryCache.containsKey(pathname)) {
