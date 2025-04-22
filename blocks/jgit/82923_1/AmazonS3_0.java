		try {
			final ByteArrayOutputStream b = new ByteArrayOutputStream();
			byte[] buf = new byte[2048];
			for (;;) {
				final int n = errorStream.read(buf);
				if (n < 0) {
					break;
				}
				if (n > 0) {
					b.write(buf
				}
			}
			buf = b.toByteArray();
			if (buf.length > 0) {
			}
		} finally {
			errorStream.close();
