
	@Inject
	private void themeChanged(
			@Optional @UIEventTopic(IThemeEngine.Events.THEME_CHANGED) Event event) {
		if (event != null) {
			resetOverriddenPreferences();
			overridePreferences(getThemeEngine(event));
		}
	}

	private void resetOverriddenPreferences() {
		for (PreferenceNode node : getPreferenceNodes()) {
			IEclipsePreferences preferences = node.getPreferenceNode();
			for (String key : node.getOverriddenPreferences().keySet()) {
				preferences.remove(key);
			}
			node.getOverriddenPreferences().clear();
		}
	}

	private HashSet<PreferenceNode> prefNodes = null;

	private Set<PreferenceNode> getPreferenceNodes() {
		if (prefNodes == null) {
			prefNodes = new HashSet<PreferenceNode>();
			try {
				for (String nodeId : InstanceScope.INSTANCE.getNode("")
						.childrenNames()) {
					prefNodes.add(new PreferenceNode(nodeId));
				}
			} catch (BackingStoreException exc) {
			}
		}
		return prefNodes;
	}

	private void overridePreferences(IThemeEngine themeEngine) {
		if (themeEngine == null) {
			return;
		}
		for (PreferenceNode node : getPreferenceNodes()) {
			themeEngine.applyStyles(node, false);
			System.err.println("Applied to " + node.getId());
		}
	}

	private IThemeEngine getThemeEngine(Event event) {
		return (IThemeEngine) event
				.getProperty(IThemeEngine.Events.THEME_ENGINE);
	}
