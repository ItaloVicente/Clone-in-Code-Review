		if (value.getCssValueType() != CSSValue.CSS_PRIMITIVE_VALUE) {
			return textToInsert;
		}

		String textTransform = ((CSSPrimitiveValue) value).getStringValue();
		if ("capitalize".equals(textTransform)) {
			String newText = StringUtils.capitalize(oldText + textToInsert);
			if (newText.length() > 0) {
				return newText.substring(newText.length() - 1);
