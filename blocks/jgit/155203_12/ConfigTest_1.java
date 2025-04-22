
	private static void writeToFile(File actFile
			throws IOException {
		try (FileOutputStream fos = new FileOutputStream(actFile)) {
			fos.write(content.getBytes(UTF_8));
		}
	}
