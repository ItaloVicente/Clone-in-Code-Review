		} else if (prop.equals(UIPreferences.THEME_UncommittedChangeBackgroundColor)
				|| prop.equals(UIPreferences.THEME_UncommittedChangeFont)
				|| prop.equals(UIPreferences.THEME_UncommittedChangeForegroundColor)
				|| prop.equals(UIPreferences.THEME_IgnoredResourceFont)
				|| prop.equals(UIPreferences.THEME_IgnoredResourceBackgroundColor)
				|| prop.equals(UIPreferences.THEME_IgnoredResourceForegroundColor)) {
			ensureFontAndColorsCreated(FONT_IDS, COLOR_IDS);
