		final Format<?> fmt;
		if (format == null && suffix != null)
			fmt = formatBySuffix(suffix);
		else if (format == null)
			fmt = lookupFormat("tar");
		else
			fmt = lookupFormat(format);
