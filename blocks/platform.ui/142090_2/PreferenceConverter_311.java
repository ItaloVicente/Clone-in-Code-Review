	public static FontData getFontData(IPreferenceStore store, String name) {
		return getFontDataArray(store, name)[0];
	}

	public static Point getPoint(IPreferenceStore store, String name) {
		return basicGetPoint(store.getString(name));
	}

	public static Rectangle getRectangle(IPreferenceStore store, String name) {
		return basicGetRectangle(store.getString(name));
	}

	public static void setDefault(IPreferenceStore store, String name,
			FontData value) {
		FontData[] fontDatas = new FontData[1];
		fontDatas[0] = value;
		setDefault(store, name, fontDatas);
	}

	public static void setDefault(IPreferenceStore store, String name,
			FontData[] value) {
		store.setDefault(name, getStoredRepresentation(value));
	}

	public static void setDefault(IPreferenceStore store, String name,
			Point value) {
		store.setDefault(name, StringConverter.asString(value));
	}

	public static void setDefault(IPreferenceStore store, String name,
			Rectangle value) {
		store.setDefault(name, StringConverter.asString(value));
	}

	public static void setDefault(IPreferenceStore store, String name, RGB value) {
		store.setDefault(name, StringConverter.asString(value));
	}

