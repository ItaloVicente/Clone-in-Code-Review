			}
		}
		return fontData;
	}

	public static FontData[] readFontData(String fontDataValue) {
		return basicGetFontData(fontDataValue);
	}

	private static Point basicGetPoint(String value) {
		Point dp = new Point(POINT_DEFAULT_DEFAULT.x, POINT_DEFAULT_DEFAULT.y);
		if (IPreferenceStore.STRING_DEFAULT_DEFAULT.equals(value)) {
