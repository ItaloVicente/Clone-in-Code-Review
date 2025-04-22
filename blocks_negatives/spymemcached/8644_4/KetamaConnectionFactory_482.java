	/**
	 * Create a KetamaConnectionFactory with the given maximum operation
	 * queue length, and the given read buffer size.
	 *
	 * @param opQueueMaxBlockTime the maximum time to block waiting for op
	 *        queue operations to complete, in milliseconds
	 */
	public KetamaConnectionFactory(int qLen, int bufSize,
			long opQueueMaxBlockTime) {
		super(qLen, bufSize, HashAlgorithm.KETAMA_HASH);
	}
