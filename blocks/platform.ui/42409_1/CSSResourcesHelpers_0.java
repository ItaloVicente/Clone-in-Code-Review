		return getCssText(fontProperties.getFamily()) + "_" + getCssText(fontProperties.getSize()) + "_"
				+ getCssText(fontProperties.getStyle()) + "_" + getCssText(fontProperties.getWeight());
	}

	private static String getCssText(CSSPrimitiveValue cssPrimitiveValue) {
		if (cssPrimitiveValue != null) {
			return cssPrimitiveValue.getCssText();
		}
		return String.valueOf(cssPrimitiveValue);
