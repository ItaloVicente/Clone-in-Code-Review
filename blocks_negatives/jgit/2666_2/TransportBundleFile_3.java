	static boolean canHandle(final URIish uri, FS fs) {
		if (uri.getHost() != null || uri.getPort() > 0 || uri.getUser() != null
				|| uri.getPass() != null || uri.getPath() == null)
			return false;

		if ("file".equals(uri.getScheme()) || uri.getScheme() == null) {
			final File f = fs.resolve(new File("."), uri.getPath());
			return f.isFile() || f.getName().endsWith(".bundle");
