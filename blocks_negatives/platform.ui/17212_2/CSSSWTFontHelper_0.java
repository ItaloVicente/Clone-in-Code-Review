		if (cssFontSize != null) {
			newFontData.setHeight((int) (cssFontSize)
					.getFloatValue(CSSPrimitiveValue.CSS_PT));
		} else {
			if (oldFontData != null)
				newFontData.setHeight(oldFontData.getHeight());
