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

	public static void setValue(IPreferenceStore store, String name,
			FontData value) {
		setValue(store, name, new FontData[] { value });
	}

	public static void setValue(IPreferenceStore store, String name,
			FontData[] value) {
		FontData[] oldValue = getFontDataArray(store, name);
		if (!Arrays.equals(oldValue, value)) {
			store.putValue(name, getStoredRepresentation(value));
			JFaceResources.getFontRegistry().put(name, value);
			store.firePropertyChangeEvent(name, oldValue, value);
		}
	}

	public static void putValue(IPreferenceStore store, String name,
			FontData[] value) {
		FontData[] oldValue = getFontDataArray(store, name);
		if (!Arrays.equals(oldValue, value)) {
			store.putValue(name, getStoredRepresentation(value));
		}
	}

	public static String getStoredRepresentation(FontData[] fontData) {
		StringBuilder buffer = new StringBuilder();
		for (FontData element : fontData) {
			if (element != null) {
				buffer.append(element.toString());
				buffer.append(ENTRY_SEPARATOR);
			}
		}
		return buffer.toString();
	}

	public static void setValue(IPreferenceStore store, String name, Point value) {
		Point oldValue = getPoint(store, name);
		if (oldValue == null || !oldValue.equals(value)) {
			store.putValue(name, StringConverter.asString(value));
			store.firePropertyChangeEvent(name, oldValue, value);
		}
	}

	public static void setValue(IPreferenceStore store, String name,
			Rectangle value) {
		Rectangle oldValue = getRectangle(store, name);
		if (oldValue == null || !oldValue.equals(value)) {
			store.putValue(name, StringConverter.asString(value));
			store.firePropertyChangeEvent(name, oldValue, value);
		}
	}

	public static void setValue(IPreferenceStore store, String name, RGB value) {
		RGB oldValue = getColor(store, name);
		if (oldValue == null || !oldValue.equals(value)) {
			store.putValue(name, StringConverter.asString(value));
			store.firePropertyChangeEvent(name, oldValue, value);
		}
	}
