		return StringConverter.asPoint(value, dp);
	}

	private static Rectangle basicGetRectangle(String value) {
		Rectangle dr = new Rectangle(RECTANGLE_DEFAULT_DEFAULT.x,
				RECTANGLE_DEFAULT_DEFAULT.y, RECTANGLE_DEFAULT_DEFAULT.width,
				RECTANGLE_DEFAULT_DEFAULT.height);

		if (IPreferenceStore.STRING_DEFAULT_DEFAULT.equals(value)) {
