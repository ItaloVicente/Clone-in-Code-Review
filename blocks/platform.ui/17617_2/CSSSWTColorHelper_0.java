		RGB rgb = getRGB((CSSPrimitiveValue) value, display);
		if (rgb != null) color = new Color(display, rgb.red, rgb.green, rgb.blue);
		return color;
	}

	private static RGB getRGB(CSSPrimitiveValue value, Display display) {
		RGB rgb = getRGB(value);
		if (rgb == null && display != null) {
			String name = value.getStringValue();
