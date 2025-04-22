	private byte[] inflateFromInput(final long sz) throws IOException {
		final byte[] dst = new byte[(int) sz];
		final Inflater inf = inflater;
		try {
			int n = 0;
			int p = -1;
			while (!inf.finished()) {
				if (inf.needsInput()) {
					if (p >= 0) {
						crc.update(buf, p, bAvail);
						use(bAvail);
					}
					p = fillFromInput(1);
					inf.setInput(buf, p, bAvail);
				}

				n += inf.inflate(dst, n, dst.length - n);
			}
			if (n != sz)
				throw new DataFormatException(JGitText.get().wrongDecompressedLength);
			n = bAvail - inf.getRemaining();
			if (n > 0) {
				crc.update(buf, p, n);
				use(n);
			}
			return dst;
		} catch (DataFormatException dfe) {
			throw corrupt(dfe);
		} finally {
			inf.reset();
		}
