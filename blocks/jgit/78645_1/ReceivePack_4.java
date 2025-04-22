public class ReceivePack {
	public static class FirstLine {
		private final String line;
		private final Set<String> capabilities;

		public FirstLine(String line) {
			final HashSet<String> caps = new HashSet<String>();
			final int nul = line.indexOf('\0');
			if (nul >= 0) {
					caps.add(c);
				this.line = line.substring(0
			} else
				this.line = line;
			this.capabilities = Collections.unmodifiableSet(caps);
		}

		public String getLine() {
			return line;
		}

		public Set<String> getCapabilities() {
			return capabilities;
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

	private StringBuilder advertiseError;

	private boolean sideBand;

	private boolean quiet;

	private List<String> pushOptions;

	private boolean usePushOptions;

	private PackLock packLock;

	private boolean checkReferencedIsReachable;

	private long maxObjectSizeLimit;

	private long maxPackSizeLimit = -1;

	private Long packSize;

	private PushCertificateParser pushCertificateParser;
	private SignedPushConfig signedPushConfig;
	private PushCertificate pushCert;

