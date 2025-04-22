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
