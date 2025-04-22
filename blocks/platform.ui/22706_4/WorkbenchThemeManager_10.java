
		protected void populateDefinition(org.eclipse.e4.ui.css.swt.theme.ITheme cssTheme,
				ITheme theme, ColorRegistry registry, ColorDefinition definition,
				IPreferenceStore store) {
			ThemeElementHelper.populateDefinition(cssTheme, theme, registry, definition, store);
		}

		protected void populateDefinition(org.eclipse.e4.ui.css.swt.theme.ITheme cssTheme,
				ITheme theme, FontRegistry registry, FontDefinition definition,
				IPreferenceStore store) {
			ThemeElementHelper.populateDefinition(cssTheme, theme, registry, definition, store);
		}
	}

	private static class ThemeRegistryModifiedHandler implements EventHandler {
		@Override
		public void handleEvent(org.osgi.service.event.Event event) {
			populateThemeRegistries(getThemeRegistry(), getFontRegistry(), getColorRegistry(),
					getTheme(), getColorsAndFontsTheme());
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

		private void populateThemeRegistries(ThemeRegistry themeRegistry,
				FontRegistry fontRegistry, ColorRegistry colorRegistry,
				org.eclipse.e4.ui.css.swt.theme.ITheme cssTheme, ITheme theme) {
			IPreferenceStore store = PrefUtil.getInternalPreferenceStore();
			for (FontDefinition definition : themeRegistry.getFonts()) {
				if (definition.isOverridden() || definition.isAddedByCss()) {
					populateDefinition(cssTheme, theme, fontRegistry, definition, store);
				}
			}
			for (ColorDefinition definition : themeRegistry.getColors()) {
				if (definition.isOverridden() || definition.isAddedByCss()) {
					populateDefinition(cssTheme, theme, colorRegistry, definition, store);
				}
			}
		}

		protected void populateDefinition(org.eclipse.e4.ui.css.swt.theme.ITheme cssTheme,
				ITheme theme, ColorRegistry registry, ColorDefinition definition,
				IPreferenceStore store) {
			ThemeElementHelper.populateDefinition(cssTheme, theme, registry, definition, store);
		}

		protected void populateDefinition(org.eclipse.e4.ui.css.swt.theme.ITheme cssTheme,
				ITheme theme, FontRegistry registry, FontDefinition definition,
				IPreferenceStore store) {
			ThemeElementHelper.populateDefinition(cssTheme, theme, registry, definition, store);
		}
