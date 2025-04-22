		throw new DataFormatException(
				"Value " + value + "doesn't represent a boolean"); //$NON-NLS-2$//$NON-NLS-1$
	}

	public static boolean asBoolean(String value, boolean dflt) {
		try {
			return asBoolean(value);
		} catch (DataFormatException e) {
			return dflt;
		}
	}

	public static double asDouble(String value) throws DataFormatException {
		try {
			return Double.parseDouble(value);
		} catch (NumberFormatException e) {
			throw new DataFormatException(e.getMessage());
		}
	}

	public static double asDouble(String value, double dflt) {
		try {
			return asDouble(value);
		} catch (DataFormatException e) {
			return dflt;
		}
	}

	public static float asFloat(String value) throws DataFormatException {
		try {
			return Float.parseFloat(value);
		} catch (NumberFormatException e) {
			throw new DataFormatException(e.getMessage());
		}
	}

	public static float asFloat(String value, float dflt) {
		try {
			return asFloat(value);
		} catch (DataFormatException e) {
			return dflt;
		}
	}

	public static FontData asFontData(String value) throws DataFormatException {
		if (value == null) {
