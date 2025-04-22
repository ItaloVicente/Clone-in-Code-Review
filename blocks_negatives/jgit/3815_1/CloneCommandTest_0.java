	public static File createTempDirectory(String name) throws IOException {
		final File temp;
		temp = File.createTempFile(name, Long.toString(System.nanoTime()));

		if (!(temp.delete())) {
			throw new IOException("Could not delete temp file: "
					+ temp.getAbsolutePath());
		}

		if (!(temp.mkdir())) {
			throw new IOException("Could not create temp directory: "
					+ temp.getAbsolutePath());
		}
		return temp;
