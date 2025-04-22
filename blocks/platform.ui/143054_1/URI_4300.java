
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
