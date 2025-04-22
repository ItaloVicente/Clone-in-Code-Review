	public byte[] toByteArray(int limit) throws IOException {
		final long len = Math.min(length()
		if (Integer.MAX_VALUE < len)
			throw new OutOfMemoryError(
					JGitText.get().lengthExceedsMaximumArraySize);
		final byte[] out = new byte[(int) len];
		int outPtr = 0;
		for (final Block b : blocks) {
			System.arraycopy(b.buffer
			outPtr += b.count;
		}
		return out;
	}

