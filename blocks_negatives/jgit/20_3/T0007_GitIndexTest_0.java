	private String content(File f) throws IOException {
		byte[] buf = new byte[(int) f.length()];
		FileInputStream is = new FileInputStream(f);
		try {
			int read = is.read(buf);
			assertEquals(f.length(), read);
			return new String(buf, 0);
		} finally {
			is.close();
		}
	}

