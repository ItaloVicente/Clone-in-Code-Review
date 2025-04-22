		boolean lightweightRefresh = ResourcesPlugin.getPlugin()
				.getPluginPreferences().getDefaultBoolean(
						ResourcesPlugin.PREF_LIGHTWEIGHT_AUTO_REFRESH);
		boolean autoRefresh = ResourcesPlugin.getPlugin()
				.getPluginPreferences().getDefaultBoolean(
						ResourcesPlugin.PREF_AUTO_REFRESH);
		autoRefreshButton.setSelection(autoRefresh);
		lightweightRefreshButton.setSelection(lightweightRefresh);
