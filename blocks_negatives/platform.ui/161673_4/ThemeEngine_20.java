	void copyFile(String from, String to) throws IOException {
		FileInputStream fStream = null;
		BufferedOutputStream outputStream = null;
		try {
			fStream = new FileInputStream(from);
			outputStream = new BufferedOutputStream(new FileOutputStream(to));
			byte[] buffer = new byte[4096];
			int c;
			while ((c = fStream.read(buffer)) != -1) {
				outputStream.write(buffer, 0, c);
			}
