	public String fileExtension()
	{
		int len = segments.length;
		if (len == 0) {
			return null;
		}

		String lastSegment = segments[len - 1];
		int i = lastSegment.lastIndexOf(FILE_EXTENSION_SEPARATOR);
		return i < 0 ? null : lastSegment.substring(i + 1);
	}


	public URI appendFileExtension(String fileExtension)
	{
		if (!validSegment(fileExtension))
		{
			throw new IllegalArgumentException(
				"invalid segment portion: " + fileExtension);
		}

		int len = segments.length;
		if (len == 0) {
			return this;
		}

		String lastSegment = segments[len - 1];
		if (SEGMENT_EMPTY.equals(lastSegment)) {
			return this;
		}
		StringBuilder newLastSegment = new StringBuilder(lastSegment);
		newLastSegment.append(FILE_EXTENSION_SEPARATOR);
		newLastSegment.append(fileExtension);

		String[] newSegments = new String[len];
		System.arraycopy(segments, 0, newSegments, 0, len - 1);
		newSegments[len - 1] = newLastSegment.toString();

		return new URI(true, scheme, authority, device, hasAbsolutePath(),
									 newSegments, query, fragment);
	}

	public URI trimFileExtension()
	{
		int len = segments.length;
		if (len == 0) {
			return this;
		}

		String lastSegment = segments[len - 1];
		int i = lastSegment.lastIndexOf(FILE_EXTENSION_SEPARATOR);
		if (i < 0) {
			return this;
		}

		String newLastSegment = lastSegment.substring(0, i);
		String[] newSegments = new String[len];
		System.arraycopy(segments, 0, newSegments, 0, len - 1);
		newSegments[len - 1] = newLastSegment;

		return new URI(true, scheme, authority, device, hasAbsolutePath(),
									 newSegments, query, fragment);
	}

	public boolean isPrefix()
	{
		return isHierarchical() && query == null && fragment == null &&
			(hasTrailingPathSeparator() || (hasAbsolutePath() && segments.length == 0));
	}

	public URI replacePrefix(URI oldPrefix, URI newPrefix)
	{
		if (!oldPrefix.isPrefix() || !newPrefix.isPrefix())
		{
			String which = oldPrefix.isPrefix() ? "new" : "old";
			throw new IllegalArgumentException("non-prefix " + which + " value");
		}

		String[] tailSegments = getTailSegments(oldPrefix);
		if (tailSegments == null) {
			return null;
		}

		String[] mergedSegments = tailSegments;
		if (newPrefix.segmentCount() != 0)
		{
			int segmentsToKeep = newPrefix.segmentCount() - 1;
			mergedSegments = new String[segmentsToKeep + tailSegments.length];
			System.arraycopy(newPrefix.segments(), 0, mergedSegments, 0,
											 segmentsToKeep);

			if (tailSegments.length != 0)
			{
				System.arraycopy(tailSegments, 0, mergedSegments, segmentsToKeep,
												 tailSegments.length);
			}
		}

		return new URI(true, newPrefix.scheme(), newPrefix.authority(),
									 newPrefix.device(), newPrefix.hasAbsolutePath(),
									 mergedSegments, query, fragment);
	}

	private String[] getTailSegments(URI prefix)
	{
		if (!prefix.isPrefix())
		{
			throw new IllegalArgumentException("non-prefix trim");
		}

		if (!isHierarchical() ||
				!equals(scheme, prefix.scheme(), true) ||
				!equals(authority, prefix.authority()) ||
				!equals(device, prefix.device()) ||
				hasAbsolutePath() != prefix.hasAbsolutePath())
		{
			return null;
		}

		if (prefix.segmentCount() == 0) {
			return segments;
		}

		int i = 0;
		int segmentsToCompare = prefix.segmentCount() - 1;
		if (segments.length <= segmentsToCompare) {
			return null;
		}

		for (; i < segmentsToCompare; i++)
		{
			if (!segments[i].equals(prefix.segment(i))) {
			return null;
		}
		}

		if (i == segments.length - 1 && SEGMENT_EMPTY.equals(segments[i]))
		{
			return NO_SEGMENTS;
		}

		String[] newSegments = new String[segments.length - i];
		System.arraycopy(segments, i, newSegments, 0, newSegments.length);
		return newSegments;
	}

	public static String encodeOpaquePart(String value, boolean ignoreEscaped)
	{
		String result = encode(value, URIC_HI, URIC_LO, ignoreEscaped);
		return result != null && result.length() > 0 && result.charAt(0) == SEGMENT_SEPARATOR ?
			"%2F" + result.substring(1) :
			result;
	}

	public static String encodeAuthority(String value, boolean ignoreEscaped)
	{
		return encode(value, SEGMENT_CHAR_HI, SEGMENT_CHAR_LO, ignoreEscaped);
	}

	public static String encodeSegment(String value, boolean ignoreEscaped)
	{
		return encode(value, SEGMENT_CHAR_HI, SEGMENT_CHAR_LO, ignoreEscaped);
	}

	public static String encodeQuery(String value, boolean ignoreEscaped)
	{
		return encode(value, URIC_HI, URIC_LO, ignoreEscaped);
	}

	public static String encodeFragment(String value, boolean ignoreEscaped)
	{
		return encode(value, URIC_HI, URIC_LO, ignoreEscaped);
