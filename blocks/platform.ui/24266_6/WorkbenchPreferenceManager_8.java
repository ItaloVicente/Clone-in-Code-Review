
	private static class CSSThemeChangedHandler implements EventHandler {
		private PreferenceManager preferenceManager;
		private Map<String, IEclipsePreferences> overriddenPrefNamesToPrefs = new HashMap<String, IEclipsePreferences>();

		public CSSThemeChangedHandler(PreferenceManager preferenceManager) {
			this.preferenceManager = preferenceManager;
		}

		@Override
		public void handleEvent(Event event) {
			resetOverriddenPreferencies();
			IThemeEngine themeEngine = (IThemeEngine) event.getProperty(IThemeEngine.Events.THEME_ENGINE);
			overridePreferences(preferenceManager.getRootSubNodes(), themeEngine);
		}

		public void dispose() {
			resetOverriddenPreferencies();
		}

		private void overridePreferences(IPreferenceNode[] nodes, IThemeEngine themeEngine) {
			for (IPreferenceNode node : nodes) {
				if (node instanceof WorkbenchPreferenceNode) {
					WorkbenchPreferenceNode wpNode = (WorkbenchPreferenceNode) node;
					PreferenceNode nodeOverridable = new PreferenceNode(wpNode.getPluginId());

					themeEngine.applyStyles(nodeOverridable, false);
					overridePreferences(wpNode, nodeOverridable.getOverriddenPreferences());
				}
			}
		}

		private void overridePreferences(WorkbenchPreferenceNode node,
				Map<String, String> toOverride) {
			if (toOverride.isEmpty()) {
				return;
			}

			IEclipsePreferences preferences = InstanceScope.INSTANCE.getNode(node.getPluginId());
			for (Map.Entry<String, String> entry : toOverride.entrySet()) {
				String existingValue = preferences.get(entry.getKey(), null);

				if (existingValue == null || existingValue.equals(entry.getValue())) {
					overriddenPrefNamesToPrefs.put(entry.getKey(), preferences);
					preferences.put(entry.getKey(), entry.getValue());
				}
			}
		}

		private void resetOverriddenPreferencies() {
			for (Map.Entry<String, IEclipsePreferences> entry : overriddenPrefNamesToPrefs
					.entrySet()) {
				entry.getValue().remove(entry.getKey());
			}
			overriddenPrefNamesToPrefs.clear();
		}
	}
