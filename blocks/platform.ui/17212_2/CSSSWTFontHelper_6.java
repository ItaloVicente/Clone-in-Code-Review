		CSSPrimitiveValue cssFontStyle = fontProperties.getStyle();
		if (fontDefinitionAsFamily && fontDataByDefinition.length > 0 && isValueFromDefinition(cssFontStyle)) {
			newFontData.setStyle(fontDataByDefinition[0].getStyle());
		} else {
			newFontData.setStyle(getSWTStyle(fontProperties, oldFontData));
		}
		
		
