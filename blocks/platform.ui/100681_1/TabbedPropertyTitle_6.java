
	public void setColor(String key, RGBA color) {
		this.factory.getColors().createColor(key, color.rgb);
	}

	public void resetSectionToolBarColors() {
		TabbedPropertySheetWidgetFactory f = new TabbedPropertySheetWidgetFactory();
		FormColors defaultColors = f.getColors();
		defaultColors.initializeSectionToolBarColors();

		FormColors colors = factory.getColors();
		colors.createColor(IFormColors.H_GRADIENT_START, defaultColors.getColor(IFormColors.H_GRADIENT_START).getRGB());
		colors.createColor(IFormColors.H_GRADIENT_END, defaultColors.getColor(IFormColors.H_GRADIENT_END).getRGB());
		colors.createColor(IFormColors.H_BOTTOM_KEYLINE1,
				defaultColors.getColor(IFormColors.H_BOTTOM_KEYLINE1).getRGB());
		colors.createColor(IFormColors.H_BOTTOM_KEYLINE2,
				defaultColors.getColor(IFormColors.H_BOTTOM_KEYLINE2).getRGB());
	}
