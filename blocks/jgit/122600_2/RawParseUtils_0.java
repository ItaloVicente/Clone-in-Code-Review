		IntList map = lineMapOrNull(buf
		if (map == null) {
			throw new BinaryBlobException();
		}
		return map;
	}

	private static @Nullable IntList lineMapOrNull(byte[] buf
