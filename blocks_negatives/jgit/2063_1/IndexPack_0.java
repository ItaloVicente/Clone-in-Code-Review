	private void inflate(final Source src, final long inflatedSize,
			final byte[] dst, final boolean keep) throws IOException {
		final Inflater inf = inflater;
		try {
			int off = 0;
			long cnt = 0;
			int p = fill(src, 24);
			inf.setInput(buf, p, bAvail);

			for (;;) {
				int r = inf.inflate(dst, off, dst.length - off);
				if (r == 0) {
					if (inf.finished())
						break;
					if (inf.needsInput()) {
						if (p >= 0) {
							crc.update(buf, p, bAvail);
							use(bAvail);
						}
						p = fill(src, 24);
						inf.setInput(buf, p, bAvail);
					} else {
						throw new CorruptObjectException(MessageFormat.format(
								JGitText.get().packfileCorruptionDetected,
								JGitText.get().unknownZlibError));
					}
				}
				cnt += r;
				if (keep)
					off += r;
			}

			if (cnt != inflatedSize) {
				throw new CorruptObjectException(MessageFormat.format(JGitText
						.get().packfileCorruptionDetected,
						JGitText.get().wrongDecompressedLength));
			}

			int left = bAvail - inf.getRemaining();
			if (left > 0) {
				crc.update(buf, p, left);
				use(left);
			}
		} catch (DataFormatException dfe) {
			throw new CorruptObjectException(MessageFormat.format(JGitText
					.get().packfileCorruptionDetected, dfe.getMessage()));
		} finally {
			inf.reset();
		}
