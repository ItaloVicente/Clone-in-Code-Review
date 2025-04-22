				Color bc = current.getColorRegistry().get(
						UIPreferences.THEME_IgnoredResourceBackgroundColor);
				Color fc = current.getColorRegistry().get(
						UIPreferences.THEME_IgnoredResourceForegroundColor);
				Font f = current.getFontRegistry().get(
						UIPreferences.THEME_IgnoredResourceFont);

				decoration.setBackgroundColor(bc);
				decoration.setForegroundColor(fc);
				decoration.setFont(f);
			} else if (!resource.isTracked()
