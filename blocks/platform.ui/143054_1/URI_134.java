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
