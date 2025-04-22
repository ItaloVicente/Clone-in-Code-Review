
	public String toFileString()
	{
		if (!isFile()) {
			return null;
		}

		StringBuilder result = new StringBuilder();
		char separator = File.separatorChar;

		if (hasAuthority())
		{
			result.append(separator);
			result.append(separator);
			result.append(authority);

			if (hasDevice()) {
			result.append(separator);
		}
		}

		if (hasDevice()) {
			result.append(device);
		}
		if (hasAbsolutePath()) {
			result.append(separator);
		}

		for (int i = 0, len = segments.length; i < len; i++)
		{
			if (i != 0) {
			result.append(separator);
		}
			result.append(segments[i]);
		}

		return decode(result.toString());
