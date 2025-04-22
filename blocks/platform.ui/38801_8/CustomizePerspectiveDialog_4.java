		return isAvailable(item) && !isHiddenItem(item, prefix);
	}

	private boolean isHiddenItem(DisplayItem item, String prefix) {
		String itemId = prefix + getCommandID(item) + ","; //$NON-NLS-1$
		return ((WorkbenchPage) window.getActivePage()).getHiddenItems().contains(itemId);
