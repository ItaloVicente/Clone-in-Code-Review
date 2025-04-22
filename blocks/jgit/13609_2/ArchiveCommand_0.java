	private static Format<?> formatBySuffix(String filenameSuffix)
			throws UnsupportedFormatException {
		for (Format<?> fmt : formats.values()) {
			for (String sfx : fmt.suffixes()) {
				if (filenameSuffix.endsWith(sfx))
					return fmt;
			}
		}
		throw new UnsupportedFormatException(filenameSuffix);
	}

