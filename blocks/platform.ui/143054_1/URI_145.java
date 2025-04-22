	private static String encodeURI(String uri, boolean ignoreEscaped, int fragmentLocationStyle)
	{
		if (uri == null) {
			return null;
		}

		StringBuilder result = new StringBuilder();

		int i = uri.indexOf(SCHEME_SEPARATOR);
		if (i != -1)
		{
			String scheme = uri.substring(0, i);
			result.append(scheme);
			result.append(SCHEME_SEPARATOR);
		}

		int j =
			fragmentLocationStyle == FRAGMENT_FIRST_SEPARATOR ? uri.indexOf(FRAGMENT_SEPARATOR) :
				fragmentLocationStyle == FRAGMENT_LAST_SEPARATOR ? uri.lastIndexOf(FRAGMENT_SEPARATOR) : -1;

		if (j != -1)
		{
			String sspart = uri.substring(++i, j);
			result.append(encode(sspart, URIC_HI, URIC_LO, ignoreEscaped));
			result.append(FRAGMENT_SEPARATOR);

			String fragment = uri.substring(++j);
			result.append(encode(fragment, URIC_HI, URIC_LO, ignoreEscaped));
		}
		else
		{
			String sspart = uri.substring(++i);
			result.append(encode(sspart, URIC_HI, URIC_LO, ignoreEscaped));
		}

		return result.toString();
	}

	private static String encode(String value, long highBitmask, long lowBitmask, boolean ignoreEscaped)
	{
		if (value == null) {
			return null;
		}

		StringBuilder result = null;

		for (int i = 0, len = value.length(); i < len; i++)
		{
			char c = value.charAt(i);

			if (!matches(c, highBitmask, lowBitmask) && c < 160 &&
					(!ignoreEscaped || !isEscaped(value, i)))
			{
				if (result == null)
				{
					result = new StringBuilder(value.substring(0, i));
				}
				appendEscaped(result, (byte)c);
			}
			else if (result != null)
			{
				result.append(c);
			}
		}
		return result == null ? value : result.toString();
	}

	private static boolean isEscaped(String s, int i)
	{
		return s.charAt(i) == ESCAPE && s.length() > i + 2 &&
			matches(s.charAt(i + 1), HEX_HI, HEX_LO) &&
			matches(s.charAt(i + 2), HEX_HI, HEX_LO);
	}

	private static void appendEscaped(StringBuilder result, byte b)
	{
		result.append(ESCAPE);

		result.append(HEX_DIGITS[(b >> 4) & 0x0F]);
		result.append(HEX_DIGITS[b & 0x0F]);
	}

	public static String decode(String value)
	{
		if (value == null) {
			return null;
		}

		int i = value.indexOf('%');
		if (i < 0)
		{
			return value;
		}
		else
		{
			StringBuilder result = new StringBuilder(value.substring(0, i));
			byte [] bytes = new byte[4];
			int receivedBytes = 0;
			int expectedBytes = 0;
			for (int len = value.length(); i < len; i++)
			{
				if (isEscaped(value, i))
				{
					char character = unescape(value.charAt(i + 1), value.charAt(i + 2));
					i += 2;

					if (expectedBytes > 0)
					{
						if ((character & 0xC0) == 0x80)
						{
							bytes[receivedBytes++] = (byte)character;
						}
						else
						{
							expectedBytes = 0;
						}
					}
					else if (character >= 0x80)
					{
						if ((character & 0xE0) == 0xC0)
						{
							bytes[receivedBytes++] = (byte)character;
							expectedBytes = 2;
						}
						else if ((character & 0xF0) == 0xE0)
						{
							bytes[receivedBytes++] = (byte)character;
							expectedBytes = 3;
						}
						else if ((character & 0xF8) == 0xF0)
						{
							bytes[receivedBytes++] = (byte)character;
							expectedBytes = 4;
						}
					}

					if (expectedBytes > 0)
					{
						if (receivedBytes == expectedBytes)
						{
							switch (receivedBytes)
							{
								case 2:
								{
									result.append((char)((bytes[0] & 0x1F) << 6 | bytes[1] & 0x3F));
									break;
								}
								case 3:
								{
									result.append((char)((bytes[0] & 0xF) << 12 | (bytes[1] & 0X3F) << 6 | bytes[2] & 0x3F));
									break;
								}
								case 4:
								{
									result.appendCodePoint(((bytes[0] & 0x7) << 18 | (bytes[1] & 0X3F) << 12 | (bytes[2] & 0X3F) << 6 | bytes[3] & 0x3F));
									break;
								}
							}
							receivedBytes = 0;
							expectedBytes = 0;
						}
					}
					else
					{
						for (int j = 0; j < receivedBytes; ++j)
						{
							result.append((char)bytes[j]);
						}
						receivedBytes = 0;
						result.append(character);
					}
				}
				else
				{
					for (int j = 0; j < receivedBytes; ++j)
					{
						result.append((char)bytes[j]);
					}
					receivedBytes = 0;
					result.append(value.charAt(i));
				}
			}
			return result.toString();
		}
	}

	private static char unescape(char highHexDigit, char lowHexDigit)
	{
		return (char)((valueOf(highHexDigit) << 4) | valueOf(lowHexDigit));
	}

	private static int valueOf(char hexDigit)
	{
		if (hexDigit >= 'A' && hexDigit <= 'F')
		{
			return hexDigit - 'A' + 10;
		}
		if (hexDigit >= 'a' && hexDigit <= 'f')
		{
			return hexDigit - 'a' + 10;
		}
		if (hexDigit >= '0' && hexDigit <= '9')
		{
			return hexDigit - '0';
		}
		return 0;
	}

