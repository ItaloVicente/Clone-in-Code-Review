		if (value.getCssValueType() == CSSValue.CSS_PRIMITIVE_VALUE) {
			CSSPrimitiveValue primitiveValue = (CSSPrimitiveValue) value;
			String textTransform = primitiveValue.getStringValue();
			if ("capitalize".equals(textTransform)) {
				String newText = StringUtils.capitalize(oldText + textToInsert);
				if (newText.length() > 0) {
					return newText.substring(newText.length() - 1);
				}
			}
			if ("uppercase".equals(textTransform)) {
				if (textToInsert != null)
					return textToInsert.toUpperCase();
			}
			if ("lowercase".equals(textTransform)) {
				if (textToInsert != null)
					return textToInsert.toLowerCase();
			}
			if ("inherit".equals(textTransform)) {
				return textToInsert;
