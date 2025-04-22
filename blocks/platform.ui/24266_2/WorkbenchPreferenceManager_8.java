
	private static class CSSThemeChangedHandler implements EventHandler {
		private PreferenceManager preferenceManager;
		private Map<String, IPreferenceStore> overriddenPropertyToStore = new HashMap<String, IPreferenceStore>();

		public CSSThemeChangedHandler(PreferenceManager preferenceManager) {
			this.preferenceManager = preferenceManager;
		}

		@Override
		public void handleEvent(Event event) {
			resetToDefaultPreferenceValues();
			IThemeEngine themeEngine = (IThemeEngine) event.getProperty(IThemeEngine.Events.THEME_ENGINE);
			overridePreferences(preferenceManager.getRootSubNodes(), themeEngine);
		}

		public void dispose() {
			resetToDefaultPreferenceValues();
		}

		private void overridePreferences(IPreferenceNode[] nodes, IThemeEngine themeEngine) {
			for (IPreferenceNode node : nodes) {
				PreferenceNode nodeOverridable = new PreferenceNode(node.getId());

				themeEngine.applyStyles(nodeOverridable, false);

				overridePreferences((org.eclipse.jface.preference.PreferenceNode) node,
						nodeOverridable.getOverriddenPreferences());

				IPreferenceNode[] subNodes = node.getSubNodes();
				if (subNodes != null && subNodes.length > 0) {
					overridePreferences(subNodes, themeEngine);
				}
			}
		}

		private void overridePreferences(org.eclipse.jface.preference.PreferenceNode node,
				Map<String, String> toOverride) {
			if (toOverride.isEmpty()) {
				return;
			}
			
			IPreferencePage page = node.getPage();
			if (page == null) {
				node.createPage();
				page = node.getPage();
				if (!(page instanceof PreferencePage)) {
					page.dispose();
					page = null;
				}
			}

			if (!(page instanceof PreferencePage)) {
				return;
			}

			IPreferenceStore store = ((PreferencePage) page).getPreferenceStore();
			for (Map.Entry<String, String> entry : toOverride.entrySet()) {
				if (!store.isDefault(entry.getKey())) {
					continue;
				}
				if (entry.getValue().length() > 0) {
					overriddenPropertyToStore.put(entry.getKey(), store);
					store.putValue(entry.getKey(), entry.getValue());
				} else {
					resetToDefaultPreferenceValue(store, entry.getKey());
				}
			}
		}

		private void resetToDefaultPreferenceValues() {
			for (Map.Entry<String, IPreferenceStore> entry : overriddenPropertyToStore.entrySet()) {
				resetToDefaultPreferenceValue(entry.getValue(), entry.getKey());
			}
			overriddenPropertyToStore.clear();
		}

		private void resetToDefaultPreferenceValue(IPreferenceStore store, String preferenceName) {
			store.putValue(preferenceName, store.getDefaultString(preferenceName));
		}
	}
