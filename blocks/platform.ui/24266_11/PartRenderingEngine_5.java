
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
			for (String key : node.getOverriddenPreferences().keySet()) {
				node.getPreferenceNode().remove(key);
			}
			node.getOverriddenPreferences().clear();
		}
	}

	private HashSet<PreferenceNode> prefNodes = null;

	private Set<PreferenceNode> getPreferenceNodes() {
		if (prefNodes == null) {
			prefNodes = new HashSet<PreferenceNode>();
			PlatformAdmin admin = WorkbenchSWTActivator.getDefault()
					.getPlatformAdmin();

			State state = admin.getState(false);
			BundleDescription[] bundles = state.getBundles();
			for (BundleDescription desc : bundles) {
				if (desc.getName() != null) {
					prefNodes.add(new PreferenceNode(desc.getName()));
				}
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
		}
	}

	private IThemeEngine getThemeEngine(Event event) {
		return (IThemeEngine) event
				.getProperty(IThemeEngine.Events.THEME_ENGINE);
	}
