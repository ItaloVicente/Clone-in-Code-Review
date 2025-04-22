		pathEncoding = getPathEncoding(rc.getString("core"
	}

	private static Charset getPathEncoding(String charsetName) {
		if (charsetName == null) {
			charsetName = SystemReader.getInstance().getProperty(
					"file.encoding");
		}
		try {
			return Charset.forName(charsetName);
		} catch (IllegalArgumentException iae) {
			throw new IllegalArgumentException(
					"Specified path encoding is not supported"
		}
	}

	public Charset getPathEncoding() {
		return pathEncoding;
