	protected FontByDefinition createFontByDefinition(CSS2FontProperties props, Font font) {
		FontData fontData = CSSSWTFontHelper.getFirstFontData(font);
		FontByDefinition fontByDefinition =
				new FontByDefinition(normalizeId(props.getFamily().getCssText().substring(1)), font);
		if (props.getSize() != null && !VALUE_FROM_FONT_DEFINITION.equals(props.getSize().getCssText())) {
			fontByDefinition.setHeight(fontData.getHeight());
		}
		if (props.getStyle() != null && !VALUE_FROM_FONT_DEFINITION.equals(props.getStyle().getCssText())) {
			fontByDefinition.setStyle(fontData.getStyle());
		}
		return fontByDefinition;
	}

	@Override
