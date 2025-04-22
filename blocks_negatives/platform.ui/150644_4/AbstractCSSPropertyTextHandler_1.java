		if (value.getCssValueType() == CSSValue.CSS_PRIMITIVE_VALUE) {
			CSSPrimitiveValue primitiveValue = (CSSPrimitiveValue) value;
			String textTransform = primitiveValue.getStringValue();
			if ("capitalize".equals(textTransform))
				return true;
			if ("uppercase".equals(textTransform))
				return true;
			if ("lowercase".equals(textTransform))
				return true;
