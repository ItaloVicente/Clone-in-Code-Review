	/** Database we write the stored objects into. */
	private final Repository db;

	/** Revision traversal support over {@link #db}. */
	private final RevWalk walk;

	/**
	 * Is the client connection a bi-directional socket or pipe?
	 * <p>
	 * If true, this class assumes it can perform multiple read and write cycles
	 * with the client over the input and output streams. This matches the
	 * functionality available with a standard TCP/IP connection, or a local
	 * operating system or in-memory pipe.
	 * <p>
	 * If false, this class runs in a read everything then output results mode,
	 * making it suitable for single round-trip systems RPCs such as HTTP.
	 */
	private boolean biDirectionalPipe = true;

	/** Expecting data after the pack footer */
	private boolean expectDataAfterPackFooter;

	/** Should an incoming transfer validate objects? */
	private ObjectChecker objectChecker;

	/** Should an incoming transfer permit create requests? */
	private boolean allowCreates;

	/** Should an incoming transfer permit delete requests? */
	private boolean allowAnyDeletes;
	private boolean allowBranchDeletes;

	/** Should an incoming transfer permit non-fast-forward requests? */
	private boolean allowNonFastForwards;

	/** Should an incoming transfer permit push options? **/
	private boolean allowPushOptions;

	/**
	 * Should the requested ref updates be performed as a single atomic
	 * transaction?
	 */
	private boolean atomic;

	private boolean allowOfsDelta;
	private boolean allowQuiet = true;

	/** Identity to record action as within the reflog. */
	private PersonIdent refLogIdent;

	/** Hook used while advertising the refs to the client. */
	private AdvertiseRefsHook advertiseRefsHook;

	/** Filter used while advertising the refs to the client. */
	private RefFilter refFilter;

	/** Timeout in seconds to wait for client interaction. */
	private int timeout;

	/** Timer to manage {@link #timeout}. */
	private InterruptTimer timer;

	private TimeoutInputStream timeoutIn;

	private OutputStream origOut;

	/** Raw input stream. */
	protected InputStream rawIn;

	/** Raw output stream. */
	protected OutputStream rawOut;

	/** Optional message output stream. */
	protected OutputStream msgOut;
	private SideBandOutputStream errOut;

	/** Packet line input stream around {@link #rawIn}. */
	protected PacketLineIn pckIn;

	/** Packet line output stream around {@link #rawOut}. */
	protected PacketLineOut pckOut;

	private final MessageOutputWrapper msgOutWrapper = new MessageOutputWrapper();

	private PackParser parser;

	/** The refs we advertised as existing at the start of the connection. */
	private Map<String, Ref> refs;

	/** All SHA-1s shown to the client, which can be possible edges. */
	private Set<ObjectId> advertisedHaves;

	/** Capabilities requested by the client. */
	private Set<String> enabledCapabilities;
	String userAgent;
	private Set<ObjectId> clientShallowCommits;
	private List<ReceiveCommand> commands;

	private StringBuilder advertiseError;

	/** If {@link BasePackPushConnection#CAPABILITY_SIDE_BAND_64K} is enabled. */
	private boolean sideBand;

	private boolean quiet;

	/**
	 * A list of option strings associated with a push.
	 * @since 4.5
	 */
	protected List<String> pushOptions;

	/**
	 * Whether the client intends to use push options.
	 * @since 4.5
	 */
	protected boolean usePushOptions;

	/** Lock around the received pack file, while updating refs. */
	private PackLock packLock;

	private boolean checkReferencedIsReachable;

	/** Git object size limit */
	private long maxObjectSizeLimit;

	/** Total pack size limit */
	private long maxPackSizeLimit = -1;

	/** The size of the received pack, including index size */
	private Long packSize;

	private PushCertificateParser pushCertificateParser;
	private SignedPushConfig signedPushConfig;
	private PushCertificate pushCert;

