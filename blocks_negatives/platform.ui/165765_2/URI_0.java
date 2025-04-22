
	/*
	 * Returns <code>true</code> if this URI contains non-ASCII characters;
	 * <code>false</code> otherwise.
	 *
	 * This unused code is included for possible future use...
	 */
/*
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
*/

	/*
	 * If this is an {@link #isIRI IRI}, converts it to a strict ASCII URI,
	 * using the procedure described in Section 3.1 of the
	 * Draft RFC</a>.  Otherwise, this URI, itself, is returned.
	 *
	 * This unused code is included for possible future use...
	 */
/*
	public URI toASCIIURI()
	{
		if (!iri) return this;

		if (cachedASCIIURI == null)
		{
			String eAuthority = encodeAsASCII(authority);
			String eDevice = encodeAsASCII(device);
			String eQuery = encodeAsASCII(query);
			String eFragment = encodeAsASCII(fragment);
			String[] eSegments = new String[segments.length];
			for (int i = 0; i < segments.length; i++)
			{
				eSegments[i] = encodeAsASCII(segments[i]);
			}
			cachedASCIIURI = new URI(hierarchical, scheme, eAuthority, eDevice, absolutePath, eSegments, eQuery, eFragment);

		}
		return cachedASCIIURI;
	}

	private String encodeAsASCII(String value)
	{
		if (value == null) return null;

		StringBuilder result = null;

		for (int i = 0, length = value.length(); i < length; i++)
		{
			char c = value.charAt(i);

			if (c >= 128)
			{
				if (result == null)
				{
					result = new StringBuilder(value.substring(0, i));
				}

				try
				{
					byte[] encoded = (new String(new char[] { c })).getBytes("UTF-8");
					for (int j = 0, encLen = encoded.length; j < encLen; j++)
					{
						appendEscaped(result, encoded[j]);
					}
				}
				catch (UnsupportedEncodingException e)
				{
					throw new WrappedException(e);
				}
			}
			else if (result != null)
			{
				result.append(c);
			}

		}
		return result == null ? value : result.toString();
	}

	private static int countEscaped(String s, int i)
	{
		int result = 0;

		for (int length = s.length(); i < length; i += 3)
		{
			if (isEscaped(s, i)) result++;
		}
		return result;
	}
*/
