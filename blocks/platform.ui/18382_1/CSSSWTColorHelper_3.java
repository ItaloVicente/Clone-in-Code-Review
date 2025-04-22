	
	public static Color getSWTColor(ColorByDefinition colorByDefinition) {
		Color color = colorByDefinition.getResource();
		IColorAndFontProvider provider = CSSActivator.getDefault().getColorAndFontProvider();		
		if (provider == null) {
			return color;
		}				
		
		RGB colorFromDefinition = provider.getColor(colorByDefinition.getSymbolicName());		
		if (colorFromDefinition != null && !colorFromDefinition.equals(color.getRGB())) {
			color = new Color(color.getDevice(), colorFromDefinition);
		}		
		return color;
	}
