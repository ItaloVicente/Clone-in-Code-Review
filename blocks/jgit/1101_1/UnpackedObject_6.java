	static long getSize(InputStream in
			throws IOException {
		try {
			in = buffer(in);
			in.mark(20);
			final byte[] hdr = new byte[64];
			IO.readFully(in

			if (isStandardFormat(hdr)) {
				in.reset();
				Inflater inf = wc.inflater();
				InputStream zIn = inflate(in
				int avail = readSome(zIn
				if (avail < 5)
					throw new CorruptObjectException(id
							JGitText.get().corruptObjectNoHeader);

				final MutableInteger p = new MutableInteger();
				Constants.decodeTypeString(id
				long size = RawParseUtils.parseLongBase10(hdr
				if (size < 0)
					throw new CorruptObjectException(id
							JGitText.get().corruptObjectNegativeSize);
				return size;

			} else {
				readSome(in
				int c = hdr[0] & 0xff;
				long size = c & 15;
				int shift = 4;
				int p = 1;
				while ((c & 0x80) != 0) {
					c = hdr[p++] & 0xff;
					size += (c & 0x7f) << shift;
					shift += 7;
				}
				return size;
			}
		} catch (ZipException badStream) {
			throw new CorruptObjectException(id
					JGitText.get().corruptObjectBadStream);
		}
	}

