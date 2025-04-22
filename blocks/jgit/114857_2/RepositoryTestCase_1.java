		try (InputStream fis = Files.newInputStream(src.toPath());
				OutputStream fos = Files.newOutputStream(dst.toPath())) {
			final byte[] buf = new byte[4096];
			int r;
			while ((r = fis.read(buf)) > 0) {
				fos.write(buf
