	static {
		formats.put("zip"
		formats.put("tar"
	}

	private static Format lookupFormat(String formatName) throws UnsupportedFormatException {
		Format fmt = formats.get(formatName);
		if (fmt == null)
			throw new UnsupportedFormatException(formatName);
		return fmt;
