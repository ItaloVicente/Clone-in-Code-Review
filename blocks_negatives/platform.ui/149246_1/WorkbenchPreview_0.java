	/**
	 * Set the tab style from preferences.
	 */
	protected void setTabStyle() {
		boolean traditionalTab = PlatformUI.getPreferenceStore()
				.getBoolean(IWorkbenchPreferenceConstants.SHOW_TRADITIONAL_STYLE_TABS);
		folder.setSimple(traditionalTab);
	}
