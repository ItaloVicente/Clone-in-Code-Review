	public String userInfo()
	{
		if (!hasAuthority()) {
			return null;
		}

		int i = authority.indexOf(USER_INFO_SEPARATOR);
		return i < 0 ? null : authority.substring(0, i);
