	public String port()
	{
		if (!hasAuthority()) {
			return null;
		}

		int i = authority.indexOf(PORT_SEPARATOR);
		return i < 0 ? null : authority.substring(i + 1);
