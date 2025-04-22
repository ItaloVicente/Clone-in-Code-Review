	private String[] findRelativePath(URI base, boolean preserveRootParents)
	{
		if (base.hasRelativePath())
		{
			throw new IllegalArgumentException(
				"find relative path against base with relative path");
		}
		if (!hasAbsolutePath())
		{
			throw new IllegalArgumentException(
				"find relative path of non-absolute path");
		}

		String[] startPath = base.collapseSegments(preserveRootParents);
		String[] endPath = segments;

		int startCount = startPath.length > 0 ? startPath.length - 1 : 0;
		int endCount = endPath.length;

		int diff = 0;

		for (int count = startCount < endCount ? startCount : endCount - 1;
				 diff < count && startPath[diff].equals(endPath[diff]); diff++)
		{
		}

		int upCount = startCount - diff;
		int downCount = endCount - diff;

		if (downCount == 1 && SEGMENT_EMPTY.equals(endPath[endCount - 1]))
		{
			downCount = 0;
		}

		if (upCount + downCount == 0)
		{
			if (query == null) {
			return new String[] { SEGMENT_SELF };
		}
			return NO_SEGMENTS;
		}

		String[] result = new String[upCount + downCount];
		Arrays.fill(result, 0, upCount, SEGMENT_PARENT);
		System.arraycopy(endPath, diff, result, upCount, downCount);
		return result;
