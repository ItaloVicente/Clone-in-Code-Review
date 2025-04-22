
	public URI trimSegments(int i)
	{
		if (!isHierarchical() || i < 1) {
			return this;
		}

		String[] newSegments = NO_SEGMENTS;
		int len = segments.length - i;
		if (len > 0)
		{
			newSegments = new String[len];
			System.arraycopy(segments, 0, newSegments, 0, len);
		}
		return new URI(true, scheme, authority, device, hasAbsolutePath(),
									 newSegments, query, fragment);
