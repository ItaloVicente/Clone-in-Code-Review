
	private static class CSSThemeChangedHandler implements EventHandler {
		private HashSet<IEclipsePreferences> prefs = null;

		public CSSThemeChangedHandler(Display display) {
			if (display != null) {
				display.addListener(SWT.Dispose, new Listener() {
					@Override
					public void handleEvent(org.eclipse.swt.widgets.Event event) {
						resetOverriddenPreferences();
					}
				});
			}
		}

		@Override
		public void handleEvent(Event event) {
			resetOverriddenPreferences();
			overridePreferences(getThemeEngine(event));
		}

		private void resetOverriddenPreferences() {
			for (IEclipsePreferences preferences : getPreferences()) {
				resetOverriddenPreferences(preferences);
			}
		}

		private void resetOverriddenPreferences(IEclipsePreferences preferences) {
			for (String name : IEclipsePreferencesHelper
					.getOverriddenPropertyNames(preferences)) {
				preferences.remove(name);
			}
			IEclipsePreferencesHelper
					.removeOverriddenPropertyNames(preferences);
		}

		private Set<IEclipsePreferences> getPreferences() {
			if (prefs == null) {
				prefs = new HashSet<IEclipsePreferences>();
				PlatformAdmin admin = WorkbenchSWTActivator.getDefault()
						.getPlatformAdmin();

				State state = admin.getState(false);
				BundleDescription[] bundles = state.getBundles();

				for (BundleDescription desc : bundles) {
					if (desc.getName() != null) {
						prefs.add(InstanceScope.INSTANCE.getNode(desc.getName()));
					}
				}
			}
			return prefs;
		}

		private void overridePreferences(IThemeEngine themeEngine) {
			if (themeEngine != null) {
				for (IEclipsePreferences preferences : getPreferences()) {
					themeEngine.applyStyles(preferences, false);
				}
			}
		}

		private IThemeEngine getThemeEngine(Event event) {
			return (IThemeEngine) event
					.getProperty(IThemeEngine.Events.THEME_ENGINE);
		}
	}
