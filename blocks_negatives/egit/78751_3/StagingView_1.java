				stagedViewer);
		if (contentProvider.getCount() > getMaxLimitForListMode()) {
			return false;
		}
		contentProvider = getContentProvider(unstagedViewer);
		if (contentProvider.getCount() > getMaxLimitForListMode()) {
			return false;
		}
		return true;
