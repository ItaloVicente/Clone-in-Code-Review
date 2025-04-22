	default Path getPath(Config config
			String name
			Path defaultValue) {
		String value = config.getString(section
		if (value == null) {
			return defaultValue;
		}
		File file;
			file = fs.resolve(fs.userHome()
		} else {
			file = fs.resolve(resolveAgainst
		}
		return file.toPath();
	}
