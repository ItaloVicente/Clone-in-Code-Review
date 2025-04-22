		IntList map = lineMapOrNull(buf
		if (map == null) {
			throw new BinaryBlobException();
		}
		return map;
	}

	private static final @Nullable IntList lineMapOrNull(
			byte[] buf
