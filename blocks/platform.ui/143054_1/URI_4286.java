	public static URI createGenericURI(String scheme, String opaquePart,
										String fragment)
	{
		if (scheme == null)
		{
			throw new IllegalArgumentException("relative non-hierarchical URI");
		}

		if (isArchiveScheme(scheme))
		{
			throw new IllegalArgumentException("non-hierarchical archive URI");
		}

		validateURI(false, scheme, opaquePart, null, false, NO_SEGMENTS, null, fragment);
		return new URI(false, scheme, opaquePart, null, false, NO_SEGMENTS, null, fragment);
	}

	public static URI createHierarchicalURI(String scheme, String authority,
											String device, String query,
											String fragment)
	{
		if (scheme != null && authority == null && device == null)
		{
			throw new IllegalArgumentException(
				"absolute hierarchical URI without authority, device, path");
		}

		if (isArchiveScheme(scheme))
		{
			throw new IllegalArgumentException("archive URI with no path");
		}

		validateURI(true, scheme, authority, device, false, NO_SEGMENTS, query, fragment);
		return new URI(true, scheme, authority, device, false, NO_SEGMENTS, query, fragment);
	}

	public static URI createHierarchicalURI(String scheme, String authority,
											String device, String[] segments,
											String query, String fragment)
	{
		if (isArchiveScheme(scheme) && device != null)
		{
			throw new IllegalArgumentException("archive URI with device");
		}

		segments = fix(segments);
		validateURI(true, scheme, authority, device, true, segments, query, fragment);
		return new URI(true, scheme, authority, device, true, segments, query, fragment);
	}

	public static URI createHierarchicalURI(String[] segments, String query,
											String fragment)
	{
		segments = fix(segments);
		validateURI(true, null, null, null, false, segments, query, fragment);
		return new URI(true, null, null, null, false, segments, query, fragment);
	}

	private static String[] fix(String[] segments)
	{
		return segments == null ? NO_SEGMENTS : (String[])segments.clone();
	}

	public static URI createURI(String uri)
	{
		return createURIWithCache(uri);
	}

	public static URI createURI(String uri, boolean ignoreEscaped)
	{
		return createURIWithCache(encodeURI(uri, ignoreEscaped, FRAGMENT_LAST_SEPARATOR));
	}

	public static final int FRAGMENT_NONE = 0;

	public static final int FRAGMENT_FIRST_SEPARATOR = 1;

	public static final int FRAGMENT_LAST_SEPARATOR = 2;

	public static URI createURI(String uri, boolean ignoreEscaped, int fragmentLocationStyle)
	{
		return createURIWithCache(encodeURI(uri, ignoreEscaped, fragmentLocationStyle));
	}

	@Deprecated
	public static URI createDeviceURI(String uri)
	{
		return createURIWithCache(uri);
	}

	@Deprecated
	public static URI createURIWithCache(String uri)
	{
		int i = uri.indexOf(FRAGMENT_SEPARATOR);
		String base = i == -1 ? uri : uri.substring(0, i);
		String fragment = i == -1 ? null : uri.substring(i + 1);

		URI result = uriCache.get(base);

		if (result == null)
		{
			result = parseIntoURI(base);
			uriCache.put(base, result);
		}

		if (fragment != null)
		{
			result = result.appendFragment(fragment);
		}
		return result;
	}

	private static URI parseIntoURI(String uri)
	{
		boolean hierarchical = true;
		String scheme = null;
		String authority = null;
		String device = null;
		boolean absolutePath = false;
		String[] segments = NO_SEGMENTS;
		String query = null;
		String fragment = null;

		int i = 0;
		int j = find(uri, i, MAJOR_SEPARATOR_HI, MAJOR_SEPARATOR_LO);

		if (j < uri.length() && uri.charAt(j) == SCHEME_SEPARATOR)
		{
			scheme = uri.substring(i, j);
			i = j + 1;
		}

		boolean archiveScheme = isArchiveScheme(scheme);
		if (archiveScheme)
		{
			j = uri.lastIndexOf(ARCHIVE_SEPARATOR);
			if (j == -1)
			{
				throw new IllegalArgumentException("no archive separator");
			}
			hierarchical = true;
			authority = uri.substring(i, ++j);
			i = j;
		}
		else if (uri.startsWith(AUTHORITY_SEPARATOR, i))
		{
			i += AUTHORITY_SEPARATOR.length();
			j = find(uri, i, SEGMENT_END_HI, SEGMENT_END_LO);
			authority = uri.substring(i, j);
			i = j;
		}
		else if (scheme != null &&
						 (i == uri.length() || uri.charAt(i) != SEGMENT_SEPARATOR))
		{
			hierarchical = false;
			j = uri.indexOf(FRAGMENT_SEPARATOR, i);
			if (j == -1) {
			j = uri.length();
	}
			authority = uri.substring(i, j);
			i = j;
		}

		if (!archiveScheme && i < uri.length() && uri.charAt(i) == SEGMENT_SEPARATOR)
		{
			j = find(uri, i + 1, SEGMENT_END_HI, SEGMENT_END_LO);
			String s = uri.substring(i + 1, j);

			if (s.length() > 0 && s.charAt(s.length() - 1) == DEVICE_IDENTIFIER)
			{
				device = s;
				i = j;
			}
		}

		if (i < uri.length() && uri.charAt(i) == SEGMENT_SEPARATOR)
		{
			i++;
			absolutePath = true;
		}

		if (segmentsRemain(uri, i))
		{
			List<String> segmentList = new ArrayList<String>();

			while (segmentsRemain(uri, i))
			{
				j = find(uri, i, SEGMENT_END_HI, SEGMENT_END_LO);
				segmentList.add(uri.substring(i, j));
				i = j;

				if (i < uri.length() && uri.charAt(i) == SEGMENT_SEPARATOR)
				{
					if (!segmentsRemain(uri, ++i)) {
				segmentList.add(SEGMENT_EMPTY);
			}
				}
			}
			segments = new String[segmentList.size()];
			segmentList.toArray(segments);
		}

		if (i < uri.length() && uri.charAt(i) == QUERY_SEPARATOR)
		{
			j = uri.indexOf(FRAGMENT_SEPARATOR, ++i);
			if (j == -1) {
			j = uri.length();
		}
			query = uri.substring(i, j);
			i = j;
		}

		if (i < uri.length()) // && uri.charAt(i) == FRAGMENT_SEPARATOR (implied)
		{
			fragment = uri.substring(++i);
		}

		validateURI(hierarchical, scheme, authority, device, absolutePath, segments, query, fragment);
		return new URI(hierarchical, scheme, authority, device, absolutePath, segments, query, fragment);
	}

	private static boolean segmentsRemain(String uri, int i)
	{
		return i < uri.length() && uri.charAt(i) != QUERY_SEPARATOR &&
			uri.charAt(i) != FRAGMENT_SEPARATOR;
	}

	private static int find(String s, int i, long highBitmask, long lowBitmask)
	{
		int len = s.length();
		if (i >= len) {
			return len;
		}

		for (i = i > 0 ? i : 0; i < len; i++)
		{
			if (matches(s.charAt(i), highBitmask, lowBitmask)) {
