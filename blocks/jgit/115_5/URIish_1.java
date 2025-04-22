
	public String getHumanishName() throws IllegalArgumentException {
		if ("".equals(getPath()) || getPath() == null)
			throw new IllegalArgumentException();
		String[] elements = getPath().split("/");
		if (elements.length == 0)
			throw new IllegalArgumentException();
		String result = elements[elements.length - 1];
		if (DOT_GIT.equals(result))
			result = elements[elements.length - 2];
		else if (result.endsWith(DOT_GIT))
			result = result.substring(0
		return result;
	}

