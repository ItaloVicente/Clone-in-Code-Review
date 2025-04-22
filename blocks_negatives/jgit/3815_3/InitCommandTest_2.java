	public static File createTempDirectory(String name) throws IOException {
		final File temp;
		temp = File.createTempFile(name, Long.toString(System.nanoTime()));
		FileUtils.delete(temp);
		FileUtils.mkdir(temp);
		return temp;
	}

