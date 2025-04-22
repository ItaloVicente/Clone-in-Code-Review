		if (element instanceof ThemesExtensionElement) {
			IThemesExtension themeExtension = (IThemesExtension) ((ThemesExtensionElement) element).getNativeWidget();
			if (FONT_DEFINITION_PROP.equals(property)) {
				addDefinitions(themeExtension, true, parseSymbolicNames(value.getCssText()));
			} else if (COLOR_DEFINITION_PROP.equals(property)) {
				addDefinitions(themeExtension, false, parseSymbolicNames(value.getCssText()));
			}
