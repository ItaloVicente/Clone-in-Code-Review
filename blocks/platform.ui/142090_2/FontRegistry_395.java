	}

	public Font defaultFont() {
		return defaultFontRecord().getBaseFont();
	}

	public FontDescriptor getDescriptor(String symbolicName) {
		Assert.isNotNull(symbolicName);
		return FontDescriptor.createFrom(getFontData(symbolicName));
	}



	private FontRecord defaultFontRecord() {
		FontRecord record = stringToFontRecord.get(JFaceResources.DEFAULT_FONT);
		if (record != null) {
			return record;
		}

		FontData[] fontData = stringToFontData.get(JFaceResources.DEFAULT_FONT);
		if (fontData != null) {
			record = createFont(JFaceResources.DEFAULT_FONT, fontData);
		}

		if (record == null) {
			Font defaultFont = calculateDefaultFont();
			record = createFont(JFaceResources.DEFAULT_FONT, defaultFont.getFontData());
			defaultFont.dispose();
			stringToFontRecord.put(JFaceResources.DEFAULT_FONT, record);
		}
		return record;
	}

	private FontData[] defaultFontData() {
		return defaultFontRecord().baseData;
	}

	public FontData[] getFontData(String symbolicName) {

		Assert.isNotNull(symbolicName);
		Object result = stringToFontData.get(symbolicName);
		if (result == null) {
