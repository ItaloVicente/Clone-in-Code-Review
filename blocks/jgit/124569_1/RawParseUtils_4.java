	public static final int nextHeader(final byte[] b
		final int sz = b.length;
		while (ptr < sz) {
			final byte c = b[ptr++];
			if (c == '\n' && (ptr == sz || b[ptr] != ' '))
				return ptr;
		}
		return ptr;
	}

	public static final int headerStart(byte[] headerName
		if (ptr != 0) {
			ptr = nextLF(b
		}
		while (ptr < b.length - (headerName.length + 1)) {
			boolean found = true;
			for (int i = 0; i < headerName.length; i++) {
				if (headerName[i] != b[ptr++]) {
					found = false;
					break;
				}
			}
			if (found && b[ptr++] == ' ') {
				return ptr;
			}
			ptr = nextLF(b
		}
		return -1;
	}

