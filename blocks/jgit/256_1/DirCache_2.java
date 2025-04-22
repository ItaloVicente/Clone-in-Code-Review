	private void skipOptionalExtension(final InputStream in
			final MessageDigest md
			throws IOException {
		final byte[] b = new byte[4096];
		while (0 < sz) {
			int n = in.read(b
			if (n < 0) {
				throw new EOFException("Short read of optional DIRC extension "
						+ formatExtensionName(hdr) + "; expected another " + sz
						+ " bytes within the section.");
			}
			md.update(b
			sz -= n;
		}
	}

	private static String formatExtensionName(final byte[] hdr)
			throws UnsupportedEncodingException {
		return "'" + new String(hdr
	}

