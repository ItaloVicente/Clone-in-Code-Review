	private static boolean isWindowsJunction(Path path) {
		if (!IS_WINDOWS) {
			return false;
		}
		if (!Files.isDirectory(path
			return false;
		}
		try {
			Boolean b = (Boolean) Files.getAttribute(path
					LinkOption.NOFOLLOW_LINKS);
			return b.booleanValue();
		} catch (IOException e) {
			throw new UncheckedIOException(e);
		}
	}

	private static boolean isWindowsJunction(BasicFileAttributes attributes) {
		if (!IS_WINDOWS) {
			return false;
		}
		return attributes.isDirectory() && attributes.isOther();
	}

