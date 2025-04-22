	public String[] segments()
	{
		return segments.clone();
	}

	public List<String> segmentsList()
	{
		return Collections.unmodifiableList(Arrays.asList(segments));
