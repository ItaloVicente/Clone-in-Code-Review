	private void skipInflateFromInput(long sz) throws IOException {
		final Inflater inf = inflater;
		try {
			final byte[] dst = objectData;
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

				int free = dst.length - n;
				if (free < 8) {
					sz -= n;
					n = 0;
					free = dst.length;
				}
				n += inf.inflate(dst, n, free);
			}
			if (n != sz)
				throw new DataFormatException(JGitText.get().wrongDecompressedLength);
			n = bAvail - inf.getRemaining();
			if (n > 0) {
				crc.update(buf, p, n);
				use(n);
			}
		} catch (DataFormatException dfe) {
			throw corrupt(dfe);
		} finally {
			inf.reset();
		}
