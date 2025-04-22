		int green = (int) rgbColor.getGreen().getFloatValue(CSSPrimitiveValue.CSS_NUMBER);
		int blue = (int) rgbColor.getBlue().getFloatValue(CSSPrimitiveValue.CSS_NUMBER);
		return getHexaColorStringValue(red, green, blue);
	}

	public static String getHexaColorStringValue(int red, int green, int blue) {
		String result = "#";
