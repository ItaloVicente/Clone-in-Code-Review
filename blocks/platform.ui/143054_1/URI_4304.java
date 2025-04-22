	public String toPlatformString(boolean decode)
	{
		if (isPlatform())
		{
			StringBuilder result = new StringBuilder();
			for (int i = 1, len = segments.length; i < len; i++)
			{
				result.append('/').append(decode ? URI.decode(segments[i]) : segments[i]);
			}
			return result.toString();
		}
