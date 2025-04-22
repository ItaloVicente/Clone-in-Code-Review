	/**
	 * Parse the GPG signature from the raw buffer.
	 *
	 * @return contents of the GPG signature; {@code null} if the tag was not
	 *         signed.
	 * @since 5.11
	 */
	@Nullable
	public final byte[] getRawGpgSignature() {
