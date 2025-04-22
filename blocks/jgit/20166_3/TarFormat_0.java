		if (path.endsWith("/") && mode != FileMode.TREE)
			throw new IllegalArgumentException(MessageFormat.format(
					ArchiveText.get().pathDoesNotMatchMode
		if (!path.endsWith("/") && mode == FileMode.TREE)
			path = path + "/";

