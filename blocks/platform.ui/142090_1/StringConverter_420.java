		StringTokenizer stok = new StringTokenizer(value, ","); //$NON-NLS-1$

		try {
			String red = stok.nextToken().trim();
			String green = stok.nextToken().trim();
			String blue = stok.nextToken().trim();
			int rval = 0, gval = 0, bval = 0;
			try {
				rval = Integer.parseInt(red);
				gval = Integer.parseInt(green);
				bval = Integer.parseInt(blue);
			} catch (NumberFormatException e) {
				throw new DataFormatException(e.getMessage());
			}
			return new RGB(rval, gval, bval);
		} catch (IllegalArgumentException e) {
			throw new DataFormatException(e.getMessage());
		} catch (NoSuchElementException e) {
			throw new DataFormatException(e.getMessage());
		}
	}

	public static RGB asRGB(String value, RGB dflt) {
		try {
			return asRGB(value);
		} catch (DataFormatException e) {
			return dflt;
		}
	}

	public static String asString(double value) {
		return String.valueOf(value);
	}

	public static String asString(float value) {
		return String.valueOf(value);
	}

	public static String asString(int value) {
		return String.valueOf(value);
	}

	public static String asString(long value) {
		return String.valueOf(value);
	}

	public static String asString(Boolean value) {
		Assert.isNotNull(value);
		return String.valueOf(value.booleanValue());
	}

	public static String asString(Double value) {
		Assert.isNotNull(value);
		return String.valueOf(value.doubleValue());
	}

	public static String asString(Float value) {
		Assert.isNotNull(value);
		return String.valueOf(value.floatValue());
	}

	public static String asString(Integer value) {
		Assert.isNotNull(value);
		return String.valueOf(value.intValue());
	}

	public static String asString(Long value) {
		Assert.isNotNull(value);
		return String.valueOf(value.longValue());
	}

	public static String asString(FontData[] value) {
		StringBuilder buffer = new StringBuilder();
		for (int i = 0; i < value.length; i++) {
			buffer.append(asString(value[i]));
			if (i != value.length - 1) {
