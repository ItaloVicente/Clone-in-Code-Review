		if (end - originalEOF < 20) {
			packOut.setLength(end);
		}

		fixHeaderFooter(packcsum, packDigest.digest());
	}

	private void writeWhole(final Deflater def, final int typeCode,
			final byte[] data) throws IOException {
		int sz = data.length;
		int hdrlen = 0;
		buf[hdrlen++] = (byte) ((typeCode << 4) | sz & 15);
		sz >>>= 4;
		while (sz > 0) {
			buf[hdrlen - 1] |= 0x80;
			buf[hdrlen++] = (byte) (sz & 0x7f);
			sz >>>= 7;
		}
		packDigest.update(buf, 0, hdrlen);
		crc.update(buf, 0, hdrlen);
		packOut.write(buf, 0, hdrlen);
		def.reset();
		def.setInput(data);
		def.finish();
		while (!def.finished()) {
			final int datlen = def.deflate(buf);
			packDigest.update(buf, 0, datlen);
			crc.update(buf, 0, datlen);
			packOut.write(buf, 0, datlen);
		}
	}

	private void fixHeaderFooter(final byte[] origcsum, final byte[] tailcsum)
			throws IOException {
		final MessageDigest origDigest = Constants.newMessageDigest();
		final MessageDigest tailDigest = Constants.newMessageDigest();
		long origRemaining = originalEOF;

		packOut.seek(0);
		bAvail = 0;
		bOffset = 0;
		fill(Source.FILE, 12);

		{
			final int origCnt = (int) Math.min(bAvail, origRemaining);
			origDigest.update(buf, 0, origCnt);
			origRemaining -= origCnt;
			if (origRemaining == 0)
				tailDigest.update(buf, origCnt, bAvail - origCnt);
		}

		NB.encodeInt32(buf, 8, entryCount);
		packOut.seek(0);
		packOut.write(buf, 0, 12);
		packOut.seek(bAvail);

		packDigest.reset();
		packDigest.update(buf, 0, bAvail);
		for (;;) {
			final int n = packOut.read(buf);
			if (n < 0)
				break;
			if (origRemaining != 0) {
				final int origCnt = (int) Math.min(n, origRemaining);
				origDigest.update(buf, 0, origCnt);
				origRemaining -= origCnt;
				if (origRemaining == 0)
					tailDigest.update(buf, origCnt, n - origCnt);
			} else
				tailDigest.update(buf, 0, n);

			packDigest.update(buf, 0, n);
		}

		if (!Arrays.equals(origDigest.digest(), origcsum)
				|| !Arrays.equals(tailDigest.digest(), tailcsum))
			throw new IOException(JGitText.get().packCorruptedWhileWritingToFilesystem);

		packcsum = packDigest.digest();
		packOut.write(packcsum);
