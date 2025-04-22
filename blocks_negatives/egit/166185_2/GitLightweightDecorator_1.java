				}
				for (String actFont : actFonts) {
					theme.getFontRegistry().get(actFont);
				}
				defaultBackgroundRgb = display.getSystemColor(
						SWT.COLOR_LIST_BACKGROUND).getRGB();
