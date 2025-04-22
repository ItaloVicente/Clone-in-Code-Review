	public static long peekObjectCount(InputStream in) throws IOException {
		final int hdrLen = Constants.PACK_SIGNATURE.length + 4 + 4;
		in.mark(hdrLen);

		byte[] hdr = new byte[hdrLen];
		IO.readFully(in

		for (int k = 0; k < Constants.PACK_SIGNATURE.length; k++)
			if (hdr[k] != Constants.PACK_SIGNATURE[k])
				throw new IOException(JGitText.get().notAPACKFile);

		long vers = NB.decodeUInt32(hdr
		if (vers != 2 && vers != 3)
			throw new IOException(MessageFormat.format(
					JGitText.get().unsupportedPackVersion

		long objectCount = NB.decodeUInt32(hdr
		in.reset();
		return objectCount;
	}

