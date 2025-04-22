
	private static void setCSSTheme(Display display, IThemeEngine themeEngine,
			String cssTheme) {
		if (display.getHighContrast()) {
			themeEngine.setTheme(cssTheme, false);
		} else {
			themeEngine.restore(cssTheme);
		}
	}

	public static class StylingPreferencesHandler implements EventHandler {
		private HashSet<IEclipsePreferences> prefs = null;

		public StylingPreferencesHandler(Display display) {
			if (display != null) {
				display.addListener(SWT.Dispose,
						createOnDisplayDisposedListener());
			}
		}

		protected Listener createOnDisplayDisposedListener() {
			return new Listener() {
					@Override
					public void handleEvent(org.eclipse.swt.widgets.Event event) {
						resetOverriddenPreferences();
					}
			};
		}

		@Override
		public void handleEvent(Event event) {
			resetOverriddenPreferences();
			overridePreferences(getThemeEngine(event));
		}

		protected void resetOverriddenPreferences() {
			for (IEclipsePreferences preferences : getPreferences()) {
				resetOverriddenPreferences(preferences);
			}
		}

		protected void resetOverriddenPreferences(
				IEclipsePreferences preferences) {
			for (String name : getOverriddenPropertyNames(preferences)) {
				preferences.remove(name);
			}
			removeOverriddenPropertyNames(preferences);
		}

		protected void removeOverriddenPropertyNames(
				IEclipsePreferences preferences) {
			EclipsePreferencesHelper.removeOverriddenPropertyNames(preferences);
		}

		protected List<String> getOverriddenPropertyNames(
				IEclipsePreferences preferences) {
			return EclipsePreferencesHelper
					.getOverriddenPropertyNames(preferences);
		}

		protected Set<IEclipsePreferences> getPreferences() {
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
