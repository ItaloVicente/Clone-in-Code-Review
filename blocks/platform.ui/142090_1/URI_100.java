	public boolean isRelative()
	{
		return scheme == null;
	}

	public boolean isHierarchical()
	{
		return (hashCode & HIERARICHICAL_FLAG) != 0;
	}

	public boolean hasAuthority()
	{
		return isHierarchical() && authority != null;
	}

	public boolean hasOpaquePart()
	{
		return !isHierarchical();
	}

	public boolean hasDevice()
	{
		return device != null;
	}

	public boolean hasPath()
	{
		return hasAbsolutePath() || (authority == null && device == null);
	}

	public boolean hasAbsolutePath()
	{
		return (hashCode & ABSOLUTE_PATH_FLAG) != 0;
	}

	public boolean hasRelativePath()
	{
		return authority == null && device == null && !hasAbsolutePath();
	}

	public boolean hasEmptyPath()
	{
		return authority == null && device == null && !hasAbsolutePath() &&
			segments.length == 0;
	}

	public boolean hasQuery()
	{
		return query != null;
	}

	public boolean hasFragment()
	{
		return fragment != null;
	}

	public boolean isCurrentDocumentReference()
	{
		return authority == null && device == null && !hasAbsolutePath() &&
			segments.length == 0 && query == null;
	}

	public boolean isEmpty()
	{
		return authority == null && device == null && !hasAbsolutePath() &&
			segments.length == 0 && query == null && fragment == null;
	}

	public boolean isFile()
	{
		return isHierarchical() &&
			((isRelative() && !hasQuery()) || SCHEME_FILE.equalsIgnoreCase(scheme));
	}

	public boolean isPlatform()
	{
		return isHierarchical() && !hasAuthority() && segmentCount() >= 2 &&
			SCHEME_PLATFORM.equalsIgnoreCase(scheme);
	}

	public boolean isPlatformResource()
	{
		return isPlatform() && "resource".equals(segments[0]);
	}

	public boolean isPlatformPlugin()
	{
		return isPlatform() && "plugin".equals(segments[0]);
	}

	public boolean isArchive()
	{
		return isArchiveScheme(scheme);
	}

	public static boolean isArchiveScheme(String value)
	{
		return value != null && archiveSchemes.contains(value.toLowerCase());
	}

	@Override
	public int hashCode()
	{
		return hashCode;
	}

	@Override
	public boolean equals(Object object)
	{
		if (this == object) {
			return true;
		}
		if (!(object instanceof URI)) {
			return false;
		}
		URI uri = (URI) object;

		return hashCode == uri.hashCode() &&
			equals(scheme, uri.scheme(), true) &&
			equals(authority, isHierarchical() ? uri.authority() : uri.opaquePart()) &&
			equals(device, uri.device()) &&
			equals(query, uri.query()) &&
			equals(fragment, uri.fragment()) &&
			segmentsEqual(uri);
	}

	private boolean segmentsEqual(URI uri)
	{
		if (segments.length != uri.segmentCount()) {
			return false;
		}
		for (int i = 0, len = segments.length; i < len; i++)
		{
			if (!segments[i].equals(uri.segment(i))) {
			return false;
		}
		}
