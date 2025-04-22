		FontData newFontData = new FontData();	
		
		CSSPrimitiveValue cssFontFamily = fontProperties.getFamily();
		FontData[] fontDataByDefinition = new FontData[0];
		boolean fontDefinitionAsFamily = cssFontFamily != null 
				&& cssFontFamily.getStringValue().startsWith(FONT_DEFINITION_MARKER);		
										
		if (fontDefinitionAsFamily) {
			fontDataByDefinition = findFontDataByDefinition(cssFontFamily);
			if (fontDataByDefinition.length > 0) {
				newFontData.setName(fontDataByDefinition[0].getName());
			}
		} else if (cssFontFamily != null) {
			newFontData.setName(cssFontFamily.getStringValue());
		}		
		
		boolean fontFamilySet = newFontData.getName() != null && newFontData.getName().trim().length() > 0;
		if (!fontFamilySet && oldFontData != null) {
			newFontData.setName(oldFontData.getName());
		}
		
		
