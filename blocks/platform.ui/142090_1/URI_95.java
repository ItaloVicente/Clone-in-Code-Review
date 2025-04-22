
	public static boolean validArchiveAuthority(String value)
	{
		if (value != null && value.length() > 0 &&
				value.charAt(value.length() - 1) == ARCHIVE_IDENTIFIER)
		{
			try
			{
				URI archiveURI = createURI(value.substring(0, value.length() - 1));
				return !archiveURI.hasFragment();
			}
			catch (IllegalArgumentException e)
			{
			}
		}
