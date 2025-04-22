	private static Format<?> formatBySuffix(String filenameSuffix)
			throws UnsupportedFormatException {
		Collection<Format<?>> fmts = formats.values();
		for (Format<?> fmt : fmts) {
			Iterable<String> suffixes = fmt.suffixes();
			for (String sfx : suffixes) {
				if (filenameSuffix.endsWith(sfx))
					return fmt;
			}
		}
		throw new UnsupportedFormatException(filenameSuffix);
	}

