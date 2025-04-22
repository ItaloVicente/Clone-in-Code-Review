	private static void checkValidPathSegment(CanonicalTreeParser t)
			throws InvalidPathException {
		boolean isWindows = SystemReader.getInstance().isWindows();
		boolean isOSX = SystemReader.getInstance().isMacOS();
		boolean ignCase = isOSX || isWindows;

