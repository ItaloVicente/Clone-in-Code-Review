			for (String actFont : FONT_IDS) {
				theme.getFontRegistry().get(actFont);
			}
			theme.getFontRegistry().defaultFont();
			defaultBackgroundRgb = display
					.getSystemColor(SWT.COLOR_LIST_BACKGROUND).getRGB();
