public class SHA1 {
	private static final Logger LOG = LoggerFactory.getLogger(SHA1.class);
	private static final boolean DETECT_COLLISIONS;

	static {
		SystemReader sr = SystemReader.getInstance();
		DETECT_COLLISIONS = v != null ? Boolean.parseBoolean(v) : true;
	}

