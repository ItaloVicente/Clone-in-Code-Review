
	private static byte[] readFile(File file) throws IOException {
		byte[] result = new byte[(int)file.length()];
		FileInputStream in = new FileInputStream(file);
		for (int off = 0; off < result.length; ) {
			int read = in.read(result, off, result.length - off);
			if (read < 0)
				throw new IOException("Early EOF");
			off += read;
		}
		return result;
	}
