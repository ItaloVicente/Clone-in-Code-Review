public class ReceivePack {
	@Deprecated
	public static class FirstLine {
		private final FirstCommand command;

		public FirstLine(String line) {
			command = FirstCommand.fromLine(line);
		}

		public String getLine() {
			return command.getLine();
		}

		public Set<String> getCapabilities() {
			return command.getCapabilities();
		}
	}

	final Repository db;

	final RevWalk walk;

	private boolean biDirectionalPipe = true;

	private boolean expectDataAfterPackFooter;

	private ObjectChecker objectChecker;

	private boolean allowCreates;

	private boolean allowAnyDeletes;

	private boolean allowBranchDeletes;

	private boolean allowNonFastForwards;

	private boolean allowPushOptions;

	private boolean atomic;

	private boolean allowOfsDelta;

	private boolean allowQuiet = true;

	private PersonIdent refLogIdent;

	private AdvertiseRefsHook advertiseRefsHook;

	RefFilter refFilter;

	private int timeout;

	private InterruptTimer timer;

	private TimeoutInputStream timeoutIn;

	private OutputStream origOut;

	protected InputStream rawIn;

	protected OutputStream rawOut;

	protected OutputStream msgOut;

	private SideBandOutputStream errOut;

	protected PacketLineIn pckIn;

	protected PacketLineOut pckOut;

	private final MessageOutputWrapper msgOutWrapper = new MessageOutputWrapper();

	private PackParser parser;

	Map<String

	Set<ObjectId> advertisedHaves;

	private Set<String> enabledCapabilities;

	String userAgent;

	private Set<ObjectId> clientShallowCommits;

	private List<ReceiveCommand> commands;

	private long maxCommandBytes;

	private long maxDiscardBytes;

	private StringBuilder advertiseError;

	private boolean sideBand;

	private boolean quiet;

	private PackLock packLock;

	private boolean checkReferencedIsReachable;

	private long maxObjectSizeLimit;

	private long maxPackSizeLimit = -1;

	private Long packSize;

	private PushCertificateParser pushCertificateParser;

	private SignedPushConfig signedPushConfig;

	PushCertificate pushCert;

	private ReceivedPackStatistics stats;

