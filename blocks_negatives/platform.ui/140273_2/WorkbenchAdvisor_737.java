	 * @param window
	 *            the workbench window
	 * @param configurer
	 *            the action bar configurer object
	 * @param flags
	 *            bit mask composed from the constants
	 *            {@link #FILL_MENU_BAR FILL_MENU_BAR},
	 *            {@link #FILL_COOL_BAR FILL_COOL_BAR},
	 *            {@link #FILL_STATUS_LINE FILL_STATUS_LINE}, and
	 *            {@link #FILL_PROXY FILL_PROXY} Note: should 1st param be
	 *            IWorkbenchWindowConfigurer to be more consistent with other
	 *            methods? Note: suggest adding ActionBuilder as API, to
	 *            encapsulate the action building outside of the advisor, and to
	 *            handle the common pattern of hanging onto the action builder
	 *            in order to properly handle FILL_PROXY
	 *
	 * @deprecated since 3.1, override
	 *             {@link ActionBarAdvisor#fillActionBars(int)} instead
