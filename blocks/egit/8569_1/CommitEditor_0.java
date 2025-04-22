
	public ShowInContext getShowInContext() {
		if (commitPage != null && commitPage.isActive())
			return commitPage.getShowInContext();
		else
			return null;
	}
