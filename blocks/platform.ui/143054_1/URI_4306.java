	public URI appendSegments(String[] segments)
	{
		if (!validSegments(segments))
		{
			String s = segments == null ? "invalid segments: null" :
				"invalid segment: " + firstInvalidSegment(segments);
			throw new IllegalArgumentException(s);
		}

		if (!isHierarchical()) {
			return this;
		}

		boolean newAbsolutePath = !hasRelativePath();

		int len = this.segments.length;
		int segmentsCount = segments.length;
		String[] newSegments = new String[len + segmentsCount];
		System.arraycopy(this.segments, 0, newSegments, 0, len);
		System.arraycopy(segments, 0, newSegments, len, segmentsCount);

		return new URI(true, scheme, authority, device, newAbsolutePath,
									 newSegments, query, fragment);
