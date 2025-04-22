		if (tempFile != null && tempFile.exists())
		tempFile.delete();
		tempFile = null;
	}

	private static File copyFromStream(File file, final InputStream stream)
			throws IOException, FileNotFoundException {
		if (stream != null) {
			try (OutputStream outStream = new FileOutputStream(file)) {
				int read = 0;
				byte[] bytes = new byte[8 * 1024];
				while ((read = stream.read(bytes)) != -1) {
					outStream.write(bytes, 0, read);
				}
			} finally {
				stream.close();
			}
		}
		return file;
	}

	private static String[] splitBaseFileNameAndExtension(File file) {
		String[] result = new String[2];
		result[0] = file.getName();
		if (idx > 0) {
			result[1] = result[0].substring(idx, result[0].length());
			result[0] = result[0].substring(0, idx);
