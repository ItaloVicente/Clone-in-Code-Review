	/**
	 * @param path
	 * @throws IOException
	 */
	public static void delete(File path) throws IOException {
		Path nioPath = path.toPath();
		Files.delete(nioPath);
	}

