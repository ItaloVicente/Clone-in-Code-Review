	public static FontData[] getFontDataArrayDefaultDefault() {
		Display display = Display.getCurrent();

		if (fontDataArrayDefaultDefault == null) {
			fontDataArrayDefaultDefault = display.getSystemFont().getFontData();
		}

		return fontDataArrayDefaultDefault;
	}

