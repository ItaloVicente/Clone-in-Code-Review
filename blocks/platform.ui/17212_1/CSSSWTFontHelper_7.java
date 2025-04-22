		boolean fontHeightSet = false;
		
		if (isValueFromDefinition(cssFontSize)) {
			if (fontDefinitionAsFamily && fontDataByDefinition.length > 0) {
				newFontData.setHeight(fontDataByDefinition[0].getHeight());
				fontHeightSet = true;
			}
		} else if (cssFontSize != null) {
			newFontData.setHeight((int) (cssFontSize).getFloatValue(CSSPrimitiveValue.CSS_PT));
			fontHeightSet = true;
