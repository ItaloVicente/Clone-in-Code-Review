			this(preferencesStore, new ColorsAndFonts() {

				private final ITheme current = PlatformUI.getWorkbench()
						.getThemeManager().getCurrentTheme();

				private volatile RGB defaultBackground;

				@Override
				public Color getColor(String id) {
					return current.getColorRegistry().get(id);
				}

				@Override
				public Font getFont(String id) {
					return current.getFontRegistry().get(id);
				}

				@Override
				public Font getDefaultFont() {
					return current.getFontRegistry().defaultFont();
				}

				@Override
				public RGB getDefaultBackground() {
					if (defaultBackground == null) {
						Display display = PlatformUI.getWorkbench()
								.getDisplay();
						display.syncExec(() -> {
							defaultBackground = display
									.getSystemColor(SWT.COLOR_LIST_BACKGROUND)
									.getRGB();
						});
					}
					return defaultBackground;
				}
			});
		}

		private DecorationHelper(IPreferenceStore preferencesStore,
				ColorsAndFonts resources) {
