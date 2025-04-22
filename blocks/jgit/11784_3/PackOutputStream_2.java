		buf[n++] = b;
		return n;
	}

	private static final int ofsDelta(long diff
		p += ofsDeltaVarIntLength(diff);
		int n = p;
		buf[--n] = (byte) (diff & 0x7F);
		while ((diff >>>= 7) != 0)
			buf[--n] = (byte) (0x80 | (--diff & 0x7F));
		return p;
	}

	private static final int ofsDeltaVarIntLength(long v) {
		int n = 1;
		for (; (v >>>= 7) != 0; n++)
			--v;
