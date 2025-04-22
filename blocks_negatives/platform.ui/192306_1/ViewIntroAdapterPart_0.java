
	/**
	 * Sets whether the CoolBar/PerspectiveBar should be visible.
	 *
	 * @param visible whether the CoolBar/PerspectiveBar should be visible
	 * @since 3.1
	 */
	private void setBarVisibility(final boolean visible) {
		WorkbenchWindow window = (WorkbenchWindow) getSite().getWorkbenchWindow();

		if (visible) {
			boolean coolbarVisible = PrefUtil.getInternalPreferenceStore()
					.getBoolean(IPreferenceConstants.COOLBAR_VISIBLE);
			boolean persBarVisible = PrefUtil.getInternalPreferenceStore()
					.getBoolean(IPreferenceConstants.PERSPECTIVEBAR_VISIBLE);
			layout = (coolbarVisible != window.getCoolBarVisible())
					|| (persBarVisible != window.getPerspectiveBarVisible());
			window.setCoolBarVisible(coolbarVisible);
			window.setPerspectiveBarVisible(persBarVisible);
		} else {
			layout = !window.getCoolBarVisible() || !window.getPerspectiveBarVisible();
			window.setCoolBarVisible(false);
			window.setPerspectiveBarVisible(false);
		}

		if (layout) {
			window.getShell().layout();
		}
	}
