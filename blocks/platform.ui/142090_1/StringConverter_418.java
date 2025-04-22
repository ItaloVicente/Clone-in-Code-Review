					"Null doesn't represent a valid point"); //$NON-NLS-1$
		}
		StringTokenizer stok = new StringTokenizer(value, ","); //$NON-NLS-1$
		String x = stok.nextToken();
		String y = stok.nextToken();
		int xval = 0, yval = 0;
		try {
			xval = Integer.parseInt(x);
			yval = Integer.parseInt(y);
		} catch (NumberFormatException e) {
			throw new DataFormatException(e.getMessage());
		}
		return new Point(xval, yval);
	}

	public static Point asPoint(String value, Point dflt) {
		try {
			return asPoint(value);
		} catch (DataFormatException e) {
			return dflt;
		}
	}

