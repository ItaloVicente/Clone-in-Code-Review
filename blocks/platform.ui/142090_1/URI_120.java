	public boolean isIRI()
	{
		return iri;
	}

	private static boolean containsNonASCII(String value)
	{
		for (int i = 0, length = value.length(); i < length; i++)
		{
			if (value.charAt(i) > 127) return true;
		}
		return false;
	}
