	/** Timeout in seconds to wait for client interaction. */
	private int timeout;

	/** Timer to manage {@link #timeout}. */
	private InterruptTimer timer;

	private TimeoutInputStream timeoutIn;

	private InputStream rawIn;

	private OutputStream rawOut;

	private OutputStream msgOut;

	private final MessageOutputWrapper msgOutWrapper = new MessageOutputWrapper();

	private PacketLineIn pckIn;

	private PacketLineOut pckOut;

	private PackParser parser;

	/** The refs we advertised as existing at the start of the connection. */
	private Map<String, Ref> refs;

	/** All SHA-1s shown to the client, which can be possible edges. */
	private Set<ObjectId> advertisedHaves;

	/** Capabilities requested by the client. */
	private Set<String> enabledCapabilities;

	/** Commands to execute, as received by the client. */
	private List<ReceiveCommand> commands;

	/** Error to display instead of advertising the references. */
	private StringBuilder advertiseError;

	/** An exception caught while unpacking and fsck'ing the objects. */
	private Throwable unpackError;

	/** If {@link BasePackPushConnection#CAPABILITY_REPORT_STATUS} is enabled. */
	private boolean reportStatus;

	/** If {@link BasePackPushConnection#CAPABILITY_SIDE_BAND_64K} is enabled. */
	private boolean sideBand;

	/** Lock around the received pack file, while updating refs. */
	private PackLock packLock;

	private boolean checkReferencedIsReachable;

	/** Git object size limit */
	private long maxObjectSizeLimit;

