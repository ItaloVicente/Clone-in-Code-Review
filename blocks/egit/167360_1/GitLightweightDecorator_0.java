	private static interface ColorsAndFonts {

		Color getColor(String id);

		Font getFont(String id);

		Font getDefaultFont();

		RGB getDefaultBackground();

	}

	private static class ReloadableColorsAndFonts implements ColorsAndFonts {

		private volatile Map<String, Object> colorsOrFonts = new HashMap<>();

		private volatile RGB defaultBackground;

		public void reload() {
			final Display display = PlatformUI.getWorkbench().getDisplay();
			display.syncExec(() -> {
				Map<String, Object> newResources = new HashMap<>();
				ITheme theme = PlatformUI.getWorkbench().getThemeManager()
						.getCurrentTheme();
				for (String actColor : COLOR_IDS) {
					newResources.put(actColor,
							theme.getColorRegistry().get(actColor));
				}
				for (String actFont : FONT_IDS) {
					newResources.put(actFont,
							theme.getFontRegistry().get(actFont));
				}
				newResources.put(JFaceResources.DEFAULT_FONT,
						theme.getFontRegistry().defaultFont());
				colorsOrFonts = newResources;
				defaultBackground = display
						.getSystemColor(SWT.COLOR_LIST_BACKGROUND).getRGB();
			});
		}

		@Override
		public Color getColor(String id) {
			Color color = (Color) colorsOrFonts.get(id);
			if (color != null && color.isDisposed()) {
				return null;
			}
			return color;
		}

		@Override
		public Font getFont(String id) {
			Font font = (Font) colorsOrFonts.get(id);
			if (font != null && font.isDisposed()) {
				return null;
			}
			return font;
		}

		@Override
		public Font getDefaultFont() {
			return getFont(JFaceResources.DEFAULT_FONT);
		}

		@Override
		public RGB getDefaultBackground() {
			return defaultBackground;
		}

	}

