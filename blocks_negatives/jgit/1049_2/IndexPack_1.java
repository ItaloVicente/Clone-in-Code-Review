			next = in.read(buf, next, free);
			if (next <= 0)
				throw new EOFException(JGitText.get().packfileIsTruncated);
			bAvail += next;
		}
		return bOffset;
	}

	private int fillFromFile(final int need) throws IOException {
		if (bAvail < need) {
			int next = bOffset + bAvail;
			int free = buf.length - next;
			if (free + bAvail < need) {
				if (bAvail > 0)
					System.arraycopy(buf, bOffset, buf, 0, bAvail);
				bOffset = 0;
				next = bAvail;
				free = buf.length - next;
