
	public void reInitColor(String key, RGBA color) {
		this.factory.getColors().createColor(key, color.rgb);
	}

	public void reInitSectionToolBarColors() {
		TabbedPropertySheetWidgetFactory f = new TabbedPropertySheetWidgetFactory();
		f.getColors().initializeSectionToolBarColors();

		FormColors colors = factory.getColors();
		colors.createColor(IFormColors.H_GRADIENT_START, f.getColors().getColor(IFormColors.H_GRADIENT_START).getRGB());
		colors.createColor(IFormColors.H_GRADIENT_END, f.getColors().getColor(IFormColors.H_GRADIENT_END).getRGB());
		colors.createColor(IFormColors.H_BOTTOM_KEYLINE1,
				f.getColors().getColor(IFormColors.H_BOTTOM_KEYLINE1).getRGB());
		colors.createColor(IFormColors.H_BOTTOM_KEYLINE2,
				f.getColors().getColor(IFormColors.H_BOTTOM_KEYLINE2).getRGB());
	}
