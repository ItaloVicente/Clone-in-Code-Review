		as = trimLeadingWhitespace(a.content, as, ae);
		bs = trimLeadingWhitespace(b.content, bs, be);

		if (ae - as != be - bs)
			return false;

		while (as < ae) {
			if (a.content[as++] != b.content[bs++])
				return false;
		}
		return true;
	}

	@Override
	protected int hashLine(final byte[] raw, int ptr, int end) {
		int hash = 5381;
		ptr = trimLeadingWhitespace(raw, ptr, end);
		for (; ptr < end; ptr++) {
			hash = (hash << 5) ^ (raw[ptr] & 0xff);
		}
		return hash;
	}
}
