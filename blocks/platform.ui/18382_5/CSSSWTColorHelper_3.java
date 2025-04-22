		if (rgb != null) {
			color = new Color(display, rgb.red, rgb.green, rgb.blue);
		}
		return color;
	}

	public static Color getSWTColor(ColorByDefinition colorByDefinition) {
		Color color = colorByDefinition.getResource();
		IColorAndFontProvider provider = CSSActivator.getDefault().getColorAndFontProvider();
		if (provider == null) {
			return color;
		}

		RGB colorFromDefinition = provider.getColor(colorByDefinition.getSymbolicName());
		if (colorFromDefinition != null && !colorFromDefinition.equals(color.getRGB())) {
			Device device = color.getDevice();
			color.dispose();
			color = new Color(device, colorFromDefinition);
			colorByDefinition.setResource(color);
		}
		colorByDefinition.setValid(true);
