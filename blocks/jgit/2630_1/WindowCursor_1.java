	void copyPackAsIs(final PackFile pack
			final PackOutputStream out) throws IOException {
		MessageDigest md = null;
		if (validate) {
			md = Constants.newMessageDigest();
			byte[] buf = out.getCopyBuffer();
			pin(pack
			if (window.copy(0
				pack.setInvalid();
				throw new IOException(JGitText.get().packfileIsTruncated);
			}
			md.update(buf
		}

		long position = 12;
		long remaining = length - (12 + 20);
		while (0 < remaining) {
