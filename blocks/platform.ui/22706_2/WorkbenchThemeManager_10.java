
		protected void repopulateThemeRegistries(ThemeRegistry themeRegistry,
				FontRegistry fontRegistry, ColorRegistry colorRegistry,
				org.eclipse.e4.ui.css.swt.theme.ITheme theme, ITheme colorsAndFontsTheme) {
			getInternalInstance().repopulateThemeRegistries(themeRegistry, fontRegistry,
					colorRegistry, theme, colorsAndFontsTheme);
		}
	}

	private static class ThemeRegistryModifiedHandler implements EventHandler {
		@Override
		public void handleEvent(org.osgi.service.event.Event event) {
			ThemeRegistry themeRegistry = getThemeRegistry();
			FontRegistry fontRegistry = getFontRegistry();
			ColorRegistry colorRegistry = getColorRegistry();

			getInternalInstance().repopulateThemeRegistries(themeRegistry, fontRegistry,
					colorRegistry, getTheme(), getColorsAndFontsTheme());

			sendThemeDefinitionChangedEvent();
		}

		private org.eclipse.e4.ui.css.swt.theme.ITheme getTheme() {
			IThemeEngine themeEngine = (IThemeEngine) getContext()
					.get(IThemeEngine.class.getName());
			return themeEngine != null ? themeEngine.getActiveTheme() : null;
		}

		private ThemeRegistry getThemeRegistry() {
			return (ThemeRegistry) getContext().get(IThemeRegistry.class.getName());
		}

		private FontRegistry getFontRegistry() {
			return getColorsAndFontsTheme().getFontRegistry();
		}

		private ColorRegistry getColorRegistry() {
			return getColorsAndFontsTheme().getColorRegistry();
		}

		private ITheme getColorsAndFontsTheme() {
			return WorkbenchThemeManager.getInstance().getCurrentTheme();
		}

		private IEclipseContext getContext() {
			return WorkbenchThemeManager.getInstance().context;
		}

		private void sendThemeDefinitionChangedEvent() {
			MApplication application = (MApplication) getContext()
					.get(MApplication.class.getName());
			getInternalInstance().eventBroker.send(UIEvents.UILifeCycle.THEME_DEFINITION_CHANGED,
					application);
		}
