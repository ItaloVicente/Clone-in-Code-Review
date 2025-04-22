			byte[] raw = rawbuf.array();
			int n = rawbuf.limit();
			if (!isBinary(raw, n)) {
				rawbuf = filterClean(raw, n, opType);
				raw = rawbuf.array();
				n = rawbuf.limit();
			}
			canonLen = n;
			return new ByteArrayInputStream(raw, 0, n);
