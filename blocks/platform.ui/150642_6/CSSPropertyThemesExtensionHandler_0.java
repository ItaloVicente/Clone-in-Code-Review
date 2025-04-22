		if (!(element instanceof ThemesExtensionElement) || property == null) {
			return false;
		}

		IThemesExtension themeExtension = (IThemesExtension) ((ThemesExtensionElement) element).getNativeWidget();
		switch (property) {
		case FONT_DEFINITION_PROP:
			addDefinitions(themeExtension, true, parseSymbolicNames(value.getCssText()));
			break;
		case COLOR_DEFINITION_PROP:
			addDefinitions(themeExtension, false, parseSymbolicNames(value.getCssText()));
			break;
		default:
			return false;
