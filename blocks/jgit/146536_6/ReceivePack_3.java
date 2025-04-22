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

	private final Repository db;

	private final RevWalk walk;

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

	private RefFilter refFilter;

	private int timeout;

	private InterruptTimer timer;

	private TimeoutInputStream timeoutIn;

	private OutputStream origOut;

	private InputStream rawIn;

	private OutputStream rawOut;

	private OutputStream msgOut;

	private SideBandOutputStream errOut;

	private PacketLineIn pckIn;

	private PacketLineOut pckOut;

	private final MessageOutputWrapper msgOutWrapper = new MessageOutputWrapper();

	private PackParser parser;

	private Map<String

	private Set<ObjectId> advertisedHaves;

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

	private PushCertificate pushCert;

	private ReceivedPackStatistics stats;

