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

  /**
   * Decodes the given string by interpreting three-digit escape sequences as the bytes of a UTF-8 encoded character
   * and replacing them with the characters they represent.
   * Incomplete escape sequences are ignored and invalid UTF-8 encoded bytes are treated as extended ASCII characters.
   */
  public static String decode(String value)
  {
    if (value == null) {
		return null;
