	private static Logger LOG = LoggerFactory.getLogger(SHA1.class);
	private static final boolean DETECT_COLLISIONS;
	private static final boolean SAFE_HASH;

	static {
		SystemReader sr = SystemReader.getInstance();
		DETECT_COLLISIONS = v != null ? Boolean.parseBoolean(v) : true;

		SAFE_HASH = v != null ? Boolean.parseBoolean(v) : false;
	}

