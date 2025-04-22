		return (FontData[]) result;
	}

	public Font get(String symbolicName) {

		return getFontRecord(symbolicName).getBaseFont();
	}

	public Font getBold(String symbolicName) {

		return getFontRecord(symbolicName).getBoldFont();
	}

	public Font getItalic(String symbolicName) {

		return getFontRecord(symbolicName).getItalicFont();
	}

	private FontRecord getFontRecord(String symbolicName) {
		Assert.isNotNull(symbolicName);
		Object result = stringToFontRecord.get(symbolicName);
		if (result != null) {
