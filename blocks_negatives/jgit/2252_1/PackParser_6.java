	private RandomAccessFile packOut;

	private byte[] packcsum;

	/** If {@link #fixThin} this is the last byte of the original checksum. */
	private long originalEOF;

