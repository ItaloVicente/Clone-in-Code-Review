		CSSPrimitiveValue cssFontFamily = fontProperties.getFamily();
		if (cssFontFamily != null)
			newFontData.setName(cssFontFamily.getStringValue());
		else {
			if (oldFontData != null)
				newFontData.setName(oldFontData.getName());
