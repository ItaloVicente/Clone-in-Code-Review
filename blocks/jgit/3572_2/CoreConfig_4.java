		String pathencodingStr = rc.getString(
				ConfigConstants.CONFIG_JGIT_SECTION
				ConfigConstants.CONFIG_KEY_PATHENCODING);
		if (pathencodingStr != null)
			pathencoding = Charset.forName(pathencodingStr);
		else
			pathencoding = Constants.FILENAME_CHARSET;
