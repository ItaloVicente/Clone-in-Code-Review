		}
		return false;
	}

	private FontData makeFontData(String value) throws MissingResourceException {
		try {
			return StringConverter.asFontData(value.trim());
		} catch (DataFormatException e) {
			throw new MissingResourceException(
					"Wrong font data format. Value is: \"" + value + "\"", getClass().getName(), value); //$NON-NLS-2$//$NON-NLS-1$
		}
	}

	public void put(String symbolicName, FontData[] fontData) {
		put(symbolicName, fontData, true);
	}

	private void put(String symbolicName, FontData[] fontData, boolean update) {

		Assert.isNotNull(symbolicName);
		Assert.isNotNull(fontData);

		FontData[] existing = stringToFontData.get(symbolicName);
		if (Arrays.equals(existing, fontData)) {
