		if (in.markSupported()) {
			return parse(in);
		}
		return parse(new BufferedInputStream(in));
	}

	@Nullable
	private static LfsPointer parse(InputStream in)
			throws IOException {
		if (!in.markSupported()) {
			throw new IllegalArgumentException(
		}
		in.mark(SIZE_THRESHOLD);
		byte[] preamble = new byte[SIZE_THRESHOLD];
		int length = IO.readFully(in
		if (length < preamble.length || in.read() < 0) {
			try (BufferedReader r = new BufferedReader(new InputStreamReader(
					new ByteArrayInputStream(preamble
				LfsPointer ptr = parse(r);
				if (ptr == null) {
					in.reset();
				}
				return ptr;
			}
		}
		boolean hasVersion = checkVersion(preamble);
		in.reset();
		if (!hasVersion) {
			return null;
		}
		in.mark(FULL_SIZE_THRESHOLD);
		byte[] fullPointer = new byte[FULL_SIZE_THRESHOLD];
		length = IO.readFully(in
		if (length == fullPointer.length && in.read() >= 0) {
			in.reset();
		}
		try (BufferedReader r = new BufferedReader(new InputStreamReader(
				new ByteArrayInputStream(fullPointer
			LfsPointer ptr = parse(r);
			if (ptr == null) {
				in.reset();
			}
			return ptr;
		}
	}

	private static LfsPointer parse(BufferedReader r) throws IOException {
