		writeMonitor.update(1);
	}

	/**
	 * Get total number of bytes written since stream start.
	 *
	 * @return total number of bytes written since stream start.
	 */
	public final long length() {
		return count;
	}

	/** @return obtain the current SHA-1 digest. */
	final byte[] getDigest() {
		return md.digest();
