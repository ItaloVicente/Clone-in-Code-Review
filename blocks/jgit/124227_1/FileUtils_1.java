
	public static boolean refreshAttributeCache(Path filePath) {
		try (DirectoryStream<Path> stream = Files
				.newDirectoryStream(filePath)) {
			log.info("Refreshing stats for {}"
			return true;
		} catch (IOException e) {
			log.warn("I/O error while trying to flush attributes for path {}"
			return false;
		}
	}

