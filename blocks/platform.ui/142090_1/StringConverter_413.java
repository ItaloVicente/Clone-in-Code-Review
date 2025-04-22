	private StringConverter() {
	}

	public static String[] asArray(String value) throws DataFormatException {
		ArrayList<String> list = new ArrayList<>();
		StringTokenizer stok = new StringTokenizer(value);
		while (stok.hasMoreTokens()) {
			list.add(stok.nextToken());
		}
		String result[] = new String[list.size()];
		list.toArray(result);
		return result;
	}

	public static String[] asArray(String value, String[] dflt) {
		try {
			return asArray(value);
		} catch (DataFormatException e) {
			return dflt;
		}
	}

	public static boolean asBoolean(String value) throws DataFormatException {
		String v = value.toLowerCase();
		if (v.equals("t") || v.equals("true")) { //$NON-NLS-1$ //$NON-NLS-2$
