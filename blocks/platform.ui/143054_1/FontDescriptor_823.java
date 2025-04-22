	public FontData[] getFontData() {
		Font tempFont = createFont(Display.getCurrent());
		FontData[] result = tempFont.getFontData();
		destroyFont(tempFont);
		return result;
	}
