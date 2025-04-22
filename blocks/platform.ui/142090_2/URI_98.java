	private static boolean contains(String s, long highBitmask, long lowBitmask)
	{
		for (int i = 0, len = s.length(); i < len; i++)
		{
			if (matches(s.charAt(i), highBitmask, lowBitmask)) {
		return true;
	}
		}
		return false;
	}

