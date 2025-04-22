
	public void setBackgroundColorInFacatory(RGBA bg) {
		this.factory.getColors().createColor(IFormColors.H_GRADIENT_START, bg.rgb);
		this.factory.getColors().createColor(IFormColors.H_GRADIENT_END, bg.rgb);
		this.factory.getColors().createColor(IFormColors.H_BOTTOM_KEYLINE1, bg.rgb);
		this.factory.getColors().createColor(IFormColors.H_BOTTOM_KEYLINE2, bg.rgb);
	}
