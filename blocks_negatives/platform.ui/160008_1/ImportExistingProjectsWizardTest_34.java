	@Override
	protected void doSetUp() throws Exception {
		super.doSetUp();
		originalRefreshSetting = ResourcesPlugin.getPlugin()
				.getPluginPreferences().getBoolean(
						ResourcesPlugin.PREF_AUTO_REFRESH);
		ResourcesPlugin.getPlugin().getPluginPreferences().setValue(
				ResourcesPlugin.PREF_AUTO_REFRESH, true);
