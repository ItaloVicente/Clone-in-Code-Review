		return start;
	}

	@Nullable
	public final byte[] getRawGpgSignature() {
		byte[] raw = buffer;
		int start = getSignatureStart();
		if (start < 0) {
			return null;
		}
