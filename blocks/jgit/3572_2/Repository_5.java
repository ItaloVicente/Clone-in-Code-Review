
	public Charset getPathEncoding() {
		String encoding = getConfig().getString(
				ConfigConstants.CONFIG_JGIT_SECTION
				null
		Charset pathEncoding;
		if (encoding == null)
			pathEncoding = Constants.FILENAME_CHARSET;
		else
			pathEncoding = Charset.forName(encoding);
		return pathEncoding;
	}

