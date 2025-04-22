	@Deprecated
	public static boolean validJarAuthority(String value)
	{
		return validArchiveAuthority(value);
	}

	public static boolean validDevice(String value)
	{
		if (value == null) {
			return true;
		}
		int len = value.length();
		return len > 0 && value.charAt(len - 1) == DEVICE_IDENTIFIER &&
			!contains(value, SEGMENT_END_HI, SEGMENT_END_LO);
	}

	public static boolean validSegment(String value)
	{
		return value != null && !contains(value, SEGMENT_END_HI, SEGMENT_END_LO);


	}

	public static boolean validSegments(String[] value)
	{
		if (value == null) {
			return false;
		}
		for (int i = 0, len = value.length; i < len; i++)
		{
			if (!validSegment(value[i])) {
			return false;
		}
		}
		return true;
	}

	private static String firstInvalidSegment(String[] value)
	{
		if (value == null) {
			return null;
		}
		for (int i = 0, len = value.length; i < len; i++)
		{
			if (!validSegment(value[i])) {
			return value[i];
		}
		}
