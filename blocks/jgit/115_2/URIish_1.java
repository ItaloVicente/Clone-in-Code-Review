
	public static String getHumanishName(String aPath)
			throws IllegalArgumentException {
		if ("".equals(aPath))
			throw new IllegalArgumentException();
		if (aPath.equals(DOT_GIT))
			throw new IllegalArgumentException();
		String[] elements = aPath.split("/");
		String lastSegment = elements[elements.length - 1];
		String result = lastSegment;
		if (lastSegment.endsWith(DOT_GIT))
			result = lastSegment.substring(0
					- DOT_GIT.length());
		if (result.equals(""))
			result = elements[elements.length - 2];
		if ("".equals(result))
			throw new IllegalArgumentException();
		return result;
	}

	public String getHumanishName() {
		return getHumanishName(getPath());
	}
