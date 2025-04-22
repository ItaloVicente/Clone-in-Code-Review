	/**
	 * Create a KetamaConnectionFactory with the default parameters.
	 */
	public KetamaConnectionFactory() {
		this(DEFAULT_OP_QUEUE_LEN, DEFAULT_READ_BUFFER_SIZE,
				DEFAULT_OP_QUEUE_MAX_BLOCK_TIME);
	}
