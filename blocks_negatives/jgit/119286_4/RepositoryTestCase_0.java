		final FileInputStream fis = new FileInputStream(src);
		try {
			final FileOutputStream fos = new FileOutputStream(dst);
			try {
				final byte[] buf = new byte[4096];
				int r;
				while ((r = fis.read(buf)) > 0) {
					fos.write(buf, 0, r);
				}
			} finally {
				fos.close();
