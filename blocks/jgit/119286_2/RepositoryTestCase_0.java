		try (FileInputStream fis = new FileInputStream(src);
				FileOutputStream fos = new FileOutputStream(dst)) {
			final byte[] buf = new byte[4096];
			int r;
			while ((r = fis.read(buf)) > 0) {
				fos.write(buf
