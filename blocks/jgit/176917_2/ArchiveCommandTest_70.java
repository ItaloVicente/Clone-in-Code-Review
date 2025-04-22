	private static int getNumBytes(File archive) throws Exception {
		try (InputStream fi = Files.newInputStream(archive.toPath());
				InputStream bi = new BufferedInputStream(fi)) {
			return bi.available();
		}
	}

