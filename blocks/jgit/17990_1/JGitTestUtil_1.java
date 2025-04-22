	public static void copyTestResource(String name
			throws IOException {
		URL url = cl().getResource(CLASSPATH_TO_RESOURCES + name);
		if (url == null)
			throw new FileNotFoundException(name);
		InputStream in = url.openStream();
		try {
			FileOutputStream out = new FileOutputStream(dest);
			try {
				byte[] buf = new byte[4096];
				for (int n; (n = in.read(buf)) > 0;)
					out.write(buf
			} finally {
				out.close();
			}
		} finally {
			in.close();
		}
	}

