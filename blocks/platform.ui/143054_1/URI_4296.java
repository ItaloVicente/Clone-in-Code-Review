	public String lastSegment()
	{
		int len = segments.length;
		if (len == 0) {
			return null;
		}
		return segments[len - 1];
