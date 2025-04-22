	/**
	 * Returns whether the internal browser is used by default
	 *
	 * @return true if the internal browser is used by default
	 */
	public static boolean isDefaultUseInternalBrowser() {
		return WebBrowserUtil.canUseInternalWebBrowser();
	}

	/**
	 * Returns whether the system browser is used by default
	 *
	 * @return true if the system browser is used by default
	 */
	public static boolean isDefaultUseSystemBrowser() {
		return WebBrowserUtil.canUseSystemBrowser();
	}
