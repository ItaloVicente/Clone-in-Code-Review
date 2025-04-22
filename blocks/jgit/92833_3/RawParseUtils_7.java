	public static int prevDLF(final byte[] b
		if (ptr == b.length)
			--ptr;
		while (ptr >= 1) {
			final byte c = b[ptr--];
			if (c == '\n' && b[ptr] == '\n')
				return ptr - 1;
		}
		return -1;
	}

