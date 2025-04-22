		IntList map = lineMapOrNull(buf, ptr, end);
		if (map == null) {
			throw new BinaryBlobException();
		}
		return map;
	}

	@Nullable
	private static IntList lineMapOrNull(byte[] buf, int ptr, int end) {
