					"Null doesn't represent a valid rectangle"); //$NON-NLS-1$
		}
		StringTokenizer stok = new StringTokenizer(value, ","); //$NON-NLS-1$
		String x = stok.nextToken();
		String y = stok.nextToken();
		String width = stok.nextToken();
		String height = stok.nextToken();
		int xval = 0, yval = 0, wval = 0, hval = 0;
		try {
			xval = Integer.parseInt(x);
			yval = Integer.parseInt(y);
			wval = Integer.parseInt(width);
			hval = Integer.parseInt(height);
		} catch (NumberFormatException e) {
			throw new DataFormatException(e.getMessage());
		}
		return new Rectangle(xval, yval, wval, hval);
	}

	public static Rectangle asRectangle(String value, Rectangle dflt) {
		try {
			return asRectangle(value);
		} catch (DataFormatException e) {
			return dflt;
		}
	}

	public static RGB asRGB(String value) throws DataFormatException {
		if (value == null) {
