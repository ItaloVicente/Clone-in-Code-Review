
		StringBuilder result = new StringBuilder();
		if (hasAbsolutePath()) {
			result.append(SEGMENT_SEPARATOR);
		}

		for (int i = 0, len = segments.length; i < len; i++)
		{
			if (i != 0) {
			result.append(SEGMENT_SEPARATOR);
		}
			result.append(segments[i]);
		}
		return result.toString();
	}

	public String devicePath()
	{
		if (!hasPath()) {
			return null;
		}

		StringBuilder result = new StringBuilder();

		if (hasAuthority())
		{
			if (!isArchive()) {
