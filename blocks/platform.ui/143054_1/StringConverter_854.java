	public static FontData[] asFontDataArray(String value) {
		String[] strings = getArrayFromList(value, FONT_SEPARATOR);
		ArrayList<FontData> data = new ArrayList<>(strings.length);
		for (String string : strings) {
			try {
				data.add(StringConverter.asFontData(string));
			} catch (DataFormatException e) {
			}
		}
		return data.toArray(new FontData[data.size()]);
	}

	public static FontData asFontData(String value, FontData dflt) {
		try {
			return asFontData(value);
		} catch (DataFormatException e) {
			return dflt;
		}
	}

	public static int asInt(String value) throws DataFormatException {
		try {
			return Integer.parseInt(value);
		} catch (NumberFormatException e) {
			throw new DataFormatException(e.getMessage());
		}
	}

	public static int asInt(String value, int dflt) {
		try {
			return asInt(value);
		} catch (DataFormatException e) {
			return dflt;
		}
	}

	public static long asLong(String value) throws DataFormatException {
		try {
			return Long.parseLong(value);
		} catch (NumberFormatException e) {
			throw new DataFormatException(e.getMessage());
		}
	}

	public static long asLong(String value, long dflt) {
		try {
			return asLong(value);
		} catch (DataFormatException e) {
			return dflt;
		}
	}

	public static Point asPoint(String value) throws DataFormatException {
		if (value == null) {
