			break;
		case IThemeManager.CHANGE_CURRENT_THEME:
		case TREE_TABLE_FONT:
		case UIPreferences.THEME_UncommittedChangeBackgroundColor:
		case UIPreferences.THEME_UncommittedChangeFont:
		case UIPreferences.THEME_UncommittedChangeForegroundColor:
		case UIPreferences.THEME_IgnoredResourceFont:
		case UIPreferences.THEME_IgnoredResourceBackgroundColor:
		case UIPreferences.THEME_IgnoredResourceForegroundColor:
			ensureFontAndColorsCreated();
