	
	private static RGB findColorByDefinition(String name) {
		IColorAndFontProvider provider = CSSActivator.getDefault().getColorAndFontProvider();
		if (provider != null) {
			return provider.getColor(normalizeId(name.substring(1)));
		}
		return null;
	}
