		protected org.eclipse.e4.ui.css.swt.theme.ITheme getTheme(org.osgi.service.event.Event event) {
			org.eclipse.e4.ui.css.swt.theme.ITheme theme = (org.eclipse.e4.ui.css.swt.theme.ITheme) event
					.getProperty(IThemeEngine.Events.THEME);
			if (theme == null) {
				IThemeEngine themeEngine = (IThemeEngine) getContext().get(
						IThemeEngine.class.getName());
				theme = themeEngine != null ? themeEngine.getActiveTheme() : null;
			}
			return theme;
		}

