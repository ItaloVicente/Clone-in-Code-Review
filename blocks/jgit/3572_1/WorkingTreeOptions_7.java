		String encoding = rc.getString(ConfigConstants.CONFIG_JGIT_SECTION
				null
		if (encoding == null)
			pathEncoding = Constants.FILENAME_CHARSET;
		else
			pathEncoding = Charset.forName(encoding);
