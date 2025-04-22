		return null;
	}

	static String getxPath(String name, int zoom) {
		Matcher matcher = XPATH_PATTERN.matcher(name);
		if (matcher.find()) {
			try {
				int current = Integer.parseInt(matcher.group(1));
				int desired = (int) ((zoom / 100d) * current);
				String lead = name.substring(0, matcher.start(1));
				String tail = name.substring(matcher.end(2));
				return lead + desired + "x" + desired + tail; //$NON-NLS-1$
			} catch (RuntimeException e) {
			}
