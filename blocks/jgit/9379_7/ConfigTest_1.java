
	private static float javaVersion() {
		String versionString = System.getProperty("java.version");
		Matcher matcher = Pattern.compile("(\\d+\\.\\d+).*").matcher(
				versionString);
		matcher.matches();
		return Float.parseFloat(matcher.group(1));
	}

	private static float osVersion() {
		String versionString = System.getProperty("os.version");
		Matcher matcher = Pattern.compile("(\\d+\\.\\d+).*").matcher(
				versionString);
		matcher.matches();
		return Float.parseFloat(matcher.group(1));
	}
