
	public static URI createFileURI(String pathName)
	{
		File file = new File(pathName);
		String uri = File.separatorChar != '/' ? pathName.replace(File.separatorChar, SEGMENT_SEPARATOR) : pathName;
		uri = encode(uri, PATH_CHAR_HI, PATH_CHAR_LO, false);
		if (file.isAbsolute())
		{
			URI result = createURI((uri.charAt(0) == SEGMENT_SEPARATOR ? "file:" : "file:/") + uri);
			return result;
		}
		else
		{
			URI result = createURI(uri);
			if (result.scheme() != null)
			{
				throw new IllegalArgumentException("invalid relative pathName: " + pathName);
			}
			return result;
		}
	}

	@Deprecated
	public static URI createPlatformResourceURI(String pathName)
	{
		return createPlatformResourceURI(pathName, ENCODE_PLATFORM_RESOURCE_URIS);
	}

	public static URI createPlatformResourceURI(String pathName, boolean encode)
	{
		return createPlatformURI("platform:/resource", "platform:/resource/", pathName, encode);
	}

	public static URI createPlatformPluginURI(String pathName, boolean encode)
	{
		return createPlatformURI("platform:/plugin", "platform:/plugin/", pathName, encode);
	}

	private static URI createPlatformURI(String unrootedBase, String rootedBase, String pathName, boolean encode)
	{
		if (File.separatorChar != SEGMENT_SEPARATOR)
		{
			pathName = pathName.replace(File.separatorChar, SEGMENT_SEPARATOR);
		}

		if (encode)
		{
			pathName = encode(pathName, PATH_CHAR_HI, PATH_CHAR_LO, false);
		}
		URI result = createURI((pathName.charAt(0) == SEGMENT_SEPARATOR ? unrootedBase : rootedBase) + pathName);
		return result;
	}

	private URI(boolean hierarchical, String scheme, String authority,
					String device, boolean absolutePath, String[] segments,
					String query, String fragment)
	{
		int tmpHashCode = 0;
		if (scheme != null)
		{
			tmpHashCode ^= scheme.toLowerCase().hashCode();
		}
		tmpHashCode ^= Objects.hashCode(authority);
		tmpHashCode ^= Objects.hashCode(device);
		tmpHashCode ^= Objects.hashCode(query);
		tmpHashCode ^= Objects.hashCode(fragment);
		for (String segment : segments) {
			tmpHashCode ^= segment.hashCode();
		}

		if (hierarchical)
		{
			tmpHashCode |= HIERARICHICAL_FLAG;
		}
		else
		{
			tmpHashCode &= ~HIERARICHICAL_FLAG;
		}
		if (absolutePath)
		{
			tmpHashCode |= ABSOLUTE_PATH_FLAG;
		}
		else
		{
			tmpHashCode &= ~ABSOLUTE_PATH_FLAG;
		}
		this.hashCode = tmpHashCode;
		this.scheme = scheme == null ? null : scheme.intern();
		this.authority = authority;
		this.device = device;
		this.segments = segments;
		this.query = query;
		this.fragment = fragment;
