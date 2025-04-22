	public URI appendSegment(String segment)
	{
		if (!validSegment(segment))
		{
			throw new IllegalArgumentException("invalid segment: " + segment);
		}

		if (!isHierarchical()) {
			return this;
		}

		boolean newAbsolutePath = !hasRelativePath();

		int len = segments.length;
		String[] newSegments = new String[len + 1];
		System.arraycopy(segments, 0, newSegments, 0, len);
		newSegments[len] = segment;

		return new URI(true, scheme, authority, device, newAbsolutePath,
									 newSegments, query, fragment);
