		private void overrideAlreadyExistingDefinitions(org.osgi.service.event.Event event,
				IStylingEngine engine, ThemeRegistry themeRegistry, FontRegistry fontRegistry,
				ColorRegistry colorRegistry) {
			IPreferenceStore store = PrefUtil.getInternalPreferenceStore();
			org.eclipse.e4.ui.css.swt.theme.ITheme cssTheme = getTheme(event);
			ITheme theme = getColorsAndFontsTheme();

