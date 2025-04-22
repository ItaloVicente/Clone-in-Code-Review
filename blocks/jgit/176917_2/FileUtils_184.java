	public static boolean hasFiles(Path dir) throws IOException {
		try (Stream<Path> stream = Files.list(dir)) {
			return stream.findAny().isPresent();
		}
	}

