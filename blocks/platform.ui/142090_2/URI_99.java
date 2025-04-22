	private static boolean validate(String value, long highBitmask, long lowBitmask,
									boolean allowNonASCII, boolean allowEscaped)
	{
		for (int i = 0, length = value.length(); i < length; i++)
		{
			char c = value.charAt(i);

			if (matches(c, highBitmask, lowBitmask)) continue;
			if (allowNonASCII && c >= 160) continue;
			if (allowEscaped && isEscaped(value, i))
			{
				i += 2;
				continue;
			}
			return false;
		}
		return true;
	}
