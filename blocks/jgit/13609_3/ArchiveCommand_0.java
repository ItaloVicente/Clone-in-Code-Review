	private static Format<?> formatBySuffix(String filenameSuffix)
			throws UnsupportedFormatException {
		if (filenameSuffix != null)
			for (Format<?> fmt : formats.values())
				for (String sfx : fmt.suffixes())
					if (filenameSuffix.endsWith(sfx))
						return fmt;
		return lookupFormat("tar");
	}

