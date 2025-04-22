		public ChangeTrackingColorsAndFonts() {
			PlatformUI.getWorkbench().getThemeManager().getCurrentTheme()
					.addPropertyChangeListener(themeListener);
			reload();
		}

		private void reload() {
			Display display = PlatformUI.getWorkbench().getDisplay();
