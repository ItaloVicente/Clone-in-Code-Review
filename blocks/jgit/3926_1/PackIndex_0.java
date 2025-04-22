	public static PackIndex read(InputStream fd) throws IOException
			CorruptObjectException {
		final byte[] hdr = new byte[8];
		IO.readFully(fd
		if (isTOC(hdr)) {
			final int v = NB.decodeInt32(hdr
			switch (v) {
			case 2:
				return new PackIndexV2(fd);
			default:
				throw new IOException(MessageFormat.format(JGitText.get().unsupportedPackIndexVersion
			}
		}
		return new PackIndexV1(fd
	}

