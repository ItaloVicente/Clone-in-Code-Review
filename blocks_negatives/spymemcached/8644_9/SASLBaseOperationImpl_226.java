	private static final int SASL_CONTINUE=0x21;

	protected final String[] mech;
	protected final byte[] challenge;
	protected final String serverName;
	protected final Map<String, ?> props;
	protected final CallbackHandler cbh;
