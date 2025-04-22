	private String[] mergePath(URI base, boolean preserveRootParents)
	{
		if (base.hasRelativePath())
		{
			throw new IllegalArgumentException("merge against relative path");
		}
		if (!hasRelativePath())
		{
			throw new IllegalStateException("merge non-relative path");
		}

		int baseSegmentCount = base.segmentCount();
		int segmentCount = segments.length;
		String[] stack = new String[baseSegmentCount + segmentCount];
		int sp = 0;

		for (int i = 0; i < baseSegmentCount - 1; i++)
		{
			sp = accumulate(stack, sp, base.segment(i), preserveRootParents);
		}

		for (int i = 0; i < segmentCount; i++)
		{
			sp = accumulate(stack, sp, segments[i], preserveRootParents);
		}

		if (sp > 0 &&  (segmentCount == 0 ||
							SEGMENT_EMPTY.equals(segments[segmentCount - 1]) ||
							SEGMENT_PARENT.equals(segments[segmentCount - 1]) ||
							SEGMENT_SELF.equals(segments[segmentCount - 1])))
		{
			stack[sp++] = SEGMENT_EMPTY;
		}

		String[] result = new String[sp];
		System.arraycopy(stack, 0, result, 0, sp);
		return result;
	}

	private static int accumulate(String[] stack, int sp, String segment,
																boolean preserveRootParents)
	{
		if (SEGMENT_PARENT.equals(segment))
		{
			if (sp == 0)
			{
				if (preserveRootParents) {
				stack[sp++] = segment;
			}
			}
			else
			{
				if (SEGMENT_PARENT.equals(stack[sp - 1])) {
				stack[sp++] = segment;
			} else {
				sp--;
			}
			}
		}
		else if (!SEGMENT_EMPTY.equals(segment) && !SEGMENT_SELF.equals(segment))
		{
			stack[sp++] = segment;
		}
		return sp;
	}

	public URI deresolve(URI base)
	{
		return deresolve(base, true, false, true);
	}

	public URI deresolve(URI base, boolean preserveRootParents,
											 boolean anyRelPath, boolean shorterRelPath)
	{
		if (!base.isHierarchical() || base.isRelative()) {
			return this;
		}

		if (isRelative()) {
			return this;
		}


		if (!scheme.equalsIgnoreCase(base.scheme())) {
			return this;
		}

		if (!isHierarchical()) {
			return this;
		}

		String newAuthority = authority;
		String newDevice = device;
		boolean newAbsolutePath = hasAbsolutePath();
		String[] newSegments = segments;
		String newQuery = query;

		if (equals(authority, base.authority()) &&
				(hasDevice() || hasPath() || (!base.hasDevice() && !base.hasPath())))
		{
			newAuthority = null;

			if (equals(device, base.device()) && (hasPath() || !base.hasPath()))
			{
				newDevice = null;


				if (!anyRelPath && !shorterRelPath)
				{
				}
				else if (hasPath() == base.hasPath() && segmentsEqual(base) &&
								 equals(query, base.query()))
				{
					newAbsolutePath = false;
					newSegments = NO_SEGMENTS;
					newQuery = null;
				}
				else if (!hasPath() && !base.hasPath())
				{
					newAbsolutePath = false;
					newSegments = NO_SEGMENTS;
				}
				else if (hasCollapsableSegments(preserveRootParents))
				{
				}
				else
				{
					String[] rel = findRelativePath(base, preserveRootParents);
					if (anyRelPath || segments.length > rel.length)
					{
						newAbsolutePath = false;
						newSegments = rel;
					}
				}
			}
		}

		return new URI(true, null, newAuthority, newDevice, newAbsolutePath,
							newSegments, newQuery, fragment);
	}

	private boolean hasCollapsableSegments(boolean preserveRootParents)
	{
		if (hasRelativePath())
		{
			throw new IllegalStateException("test collapsability of relative path");
		}

		for (int i = 0, len = segments.length; i < len; i++)
		{
			String segment = segments[i];
			if ((i < len - 1 && SEGMENT_EMPTY.equals(segment)) ||
					SEGMENT_SELF.equals(segment) ||
					SEGMENT_PARENT.equals(segment) && (
						!preserveRootParents || (
							i != 0 && !SEGMENT_PARENT.equals(segments[i - 1]))))
			{
				return true;
			}
		}
		return false;
	}

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
	}

	String[] collapseSegments(boolean preserveRootParents)
	{
		if (hasRelativePath())
		{
			throw new IllegalStateException("collapse relative path");
		}

		if (!hasCollapsableSegments(preserveRootParents)) {
			return segments();
		}

		int segmentCount = segments.length;
		String[] stack = new String[segmentCount];
		int sp = 0;

		for (int i = 0; i < segmentCount; i++)
		{
			sp = accumulate(stack, sp, segments[i], preserveRootParents);
		}

		if (sp > 0 && (SEGMENT_EMPTY.equals(segments[segmentCount - 1]) ||
							SEGMENT_PARENT.equals(segments[segmentCount - 1]) ||
							SEGMENT_SELF.equals(segments[segmentCount - 1])))
		{
			stack[sp++] = SEGMENT_EMPTY;
		}

		String[] result = new String[sp];
		System.arraycopy(stack, 0, result, 0, sp);
		return result;
	}

	@Override
	public String toString()
	{
		if (cachedToString == null)
		{
			StringBuilder result = new StringBuilder();
			if (!isRelative())
			{
				result.append(scheme);
				result.append(SCHEME_SEPARATOR);
			}

			if (isHierarchical())
			{
				if (hasAuthority())
				{
					if (!isArchive()) {
				result.append(AUTHORITY_SEPARATOR);
			}
					result.append(authority);
				}

				if (hasDevice())
				{
					result.append(SEGMENT_SEPARATOR);
					result.append(device);
				}

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

				if (hasQuery())
				{
					result.append(QUERY_SEPARATOR);
					result.append(query);
				}
			}
			else
			{
				result.append(authority);
			}

			if (hasFragment())
			{
				result.append(FRAGMENT_SEPARATOR);
				result.append(fragment);
			}
			cachedToString = result.toString();
		}
		return cachedToString;
	}

	String toString(boolean includeSimpleForm)
	{
		StringBuilder result = new StringBuilder();
		if (includeSimpleForm) {
			result.append(toString());
		}
		result.append("\n hierarchical: ");
		result.append(isHierarchical());
		result.append("\n       scheme: ");
		result.append(scheme);
		result.append("\n    authority: ");
		result.append(authority);
		result.append("\n       device: ");
