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
