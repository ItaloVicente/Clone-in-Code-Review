
	private static void validateURI(boolean hierarchical, String scheme,
									String authority, String device,
									boolean absolutePath, String[] segments,
									String query, String fragment)
	{
		if (!validScheme(scheme))
		{
			throw new IllegalArgumentException("invalid scheme: " + scheme);
		}
		if (!hierarchical && !validOpaquePart(authority))
		{
			throw new IllegalArgumentException("invalid opaquePart: " + authority);
		}
		if (hierarchical && !isArchiveScheme(scheme) && !validAuthority(authority))
		{
			throw new IllegalArgumentException("invalid authority: " + authority);
		}
		if (hierarchical && isArchiveScheme(scheme) && !validArchiveAuthority(authority))
		{
			throw new IllegalArgumentException("invalid authority: " + authority);
		}
		if (!validDevice(device))
		{
			throw new IllegalArgumentException("invalid device: " + device);
		}
		if (!validSegments(segments))
		{
			String s = segments == null ? "invalid segments: null" :
				"invalid segment: " + firstInvalidSegment(segments);
			throw new IllegalArgumentException(s);
		}
		if (!validQuery(query))
		{
			throw new IllegalArgumentException("invalid query: " + query);
		}
		if (!validFragment(fragment))
		{
			throw new IllegalArgumentException("invalid fragment: " + fragment);
		}
	}


	public static boolean validScheme(String value)
	{
		return value == null || !contains(value, MAJOR_SEPARATOR_HI, MAJOR_SEPARATOR_LO);


	}

	public static boolean validOpaquePart(String value)
	{
		return value != null && value.indexOf(FRAGMENT_SEPARATOR) == -1 &&
		value.length() > 0 && value.charAt(0) != SEGMENT_SEPARATOR;


	}

	public static boolean validAuthority(String value)
	{
		return value == null || !contains(value, SEGMENT_END_HI, SEGMENT_END_LO);


	}

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
