			result.append(segments[i]);
		}
		return result.toString();
	}

	public String query()
	{
		return query;
	}


	public URI appendQuery(String query)
	{
		if (!validQuery(query))
		{
			throw new IllegalArgumentException(
				"invalid query portion: " + query);
		}
		return new URI(isHierarchical(), scheme, authority, device, hasAbsolutePath(), segments, query, fragment);
	}

	public URI trimQuery()
	{
		if (query == null)
		{
			return this;
		}
		else
		{
			return new URI(isHierarchical(), scheme, authority, device, hasAbsolutePath(), segments, null, fragment);
		}
	}

	public String fragment()
	{
		return fragment;
	}

	public URI appendFragment(String fragment)
	{
		if (!validFragment(fragment))
		{
			throw new IllegalArgumentException(
				"invalid fragment portion: " + fragment);
		}
		URI result = new URI(isHierarchical(), scheme, authority, device, hasAbsolutePath(), segments, query, fragment);

		if (!hasFragment())
		{
			result.cachedTrimFragment = this;
		}
		return result;
	}

	public URI trimFragment()
	{
		if (fragment == null)
		{
			return this;
		}
		else if (cachedTrimFragment == null)
		{
			cachedTrimFragment = new URI(isHierarchical(), scheme, authority, device, hasAbsolutePath(), segments, query, null);
		}

		return cachedTrimFragment;
	}

	public URI resolve(URI base)
	{
		return resolve(base, true);
	}

	public URI resolve(URI base, boolean preserveRootParents)
	{
		if (!base.isHierarchical() || base.isRelative())
		{
			throw new IllegalArgumentException(
				"resolve against non-hierarchical or relative base");
		}

		if (!isRelative()) {
			return this;
		}


		String newAuthority = authority;
		String newDevice = device;
		boolean newAbsolutePath = hasAbsolutePath();
		String[] newSegments = segments;
		String newQuery = query;

		if (authority == null)
		{
			newAuthority = base.authority();

			if (device == null)
			{
				newDevice = base.device();

				if (hasEmptyPath() && query == null)
				{
					newAbsolutePath = base.hasAbsolutePath();
					newSegments = base.segments();
					newQuery = base.query();
				}
				else if (hasRelativePath())
				{
					newAbsolutePath = base.hasAbsolutePath() || !hasEmptyPath();
					newSegments = newAbsolutePath ? mergePath(base, preserveRootParents)
						: NO_SEGMENTS;
				}
			}
		}

		return new URI(true, base.scheme(), newAuthority, newDevice,
						newAbsolutePath, newSegments, newQuery, fragment);
	}

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
