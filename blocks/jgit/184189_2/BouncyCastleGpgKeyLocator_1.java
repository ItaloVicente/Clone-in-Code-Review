	}

	private static Path checkDirectory(String dir
			Function<String
		if (!StringUtils.isEmptyOrNull(dir)) {
			try {
				Path directory = toPath.apply(dir);
				if (Files.isDirectory(directory)) {
					return directory;
				}
			} catch (SecurityException | InvalidPathException e) {
			}
			if (warn != null) {
				warn.accept(dir);
			}
