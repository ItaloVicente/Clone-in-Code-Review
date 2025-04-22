	private final CRC32 crc = new CRC32();

	private final MessageDigest md = Constants.newMessageDigest();

	private long count;

	PackOutputStream(final OutputStream out) {
		this.out = out;
