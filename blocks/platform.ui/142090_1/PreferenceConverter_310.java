		return StringConverter.asRectangle(value, dr);
	}

	public static RGB getColor(IPreferenceStore store, String name) {
		return basicGetColor(store.getString(name));
	}

	public static RGB getDefaultColor(IPreferenceStore store, String name) {
		return basicGetColor(store.getDefaultString(name));
	}

	public static FontData[] getDefaultFontDataArray(IPreferenceStore store,
			String name) {
		return basicGetFontData(store.getDefaultString(name));
	}

	public static FontData getDefaultFontData(IPreferenceStore store,
			String name) {
		return getDefaultFontDataArray(store, name)[0];
	}

	public static Point getDefaultPoint(IPreferenceStore store, String name) {
		return basicGetPoint(store.getDefaultString(name));
	}

	public static Rectangle getDefaultRectangle(IPreferenceStore store,
			String name) {
		return basicGetRectangle(store.getDefaultString(name));
	}

	public static FontData[] getFontDataArray(IPreferenceStore store,
			String name) {
		return basicGetFontData(store.getString(name));
	}
