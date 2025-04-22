	private final boolean packZeros;

	/**
	 * Get an instance of TranscoderUtils.
	 *
	 * @param pack if true, remove all zero bytes from the MSB of the packed num
	 */
	public TranscoderUtils(boolean pack) {
		super();
		packZeros=pack;
	}
