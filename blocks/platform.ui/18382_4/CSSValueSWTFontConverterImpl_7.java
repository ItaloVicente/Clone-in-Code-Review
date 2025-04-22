			Font font = new Font(display, fontData);

			if (CSSSWTFontHelper.hasFontDefinitionAsFamily(value)) {
				return createFontByDefinition((CSS2FontProperties) value, font);
			}
			return font;
