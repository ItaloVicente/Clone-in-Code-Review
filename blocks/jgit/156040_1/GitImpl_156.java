	private static final Logger LOG = LoggerFactory.getLogger(GitImpl.class);
	private static final String DEFAULT_JGIT_RETRY_SLEEP_TIME = "50";
	private static int JGIT_RETRY_TIMES = initRetryValue();
	private static final int JGIT_RETRY_SLEEP_TIME = initSleepTime();
	private boolean isEnabled = false;

	private static int initSleepTime() {
		final ConfigProperties config = new ConfigProperties(System.getProperties());
		return config.get("nio.git.retry.onfail.sleep"
	}

	private static int initRetryValue() {
		final ConfigProperties config = new ConfigProperties(System.getProperties());
		final String osName = config.get("os.name"
		final String defaultRetryTimes;
		if (osName.toLowerCase().contains("windows")) {
			defaultRetryTimes = "10";
		} else {
			defaultRetryTimes = "0";
		}
		try {
			return config.get("nio.git.retry.onfail.times"
		} catch (NumberFormatException ex) {
			return 0;
		}
	}

	private org.eclipse.jgit.api.Git git;
	private KetchLeaderCache leaders;
	private final AtomicBoolean isHeadInitialized = new AtomicBoolean(false);

	public GitImpl(final org.eclipse.jgit.api.Git git) {
		this(git
	}

	public GitImpl(final org.eclipse.jgit.api.Git git
		this.git = git;
		this.leaders = leaders;
	}

	@Override
	public void convertRefTree() {
		try {
			new ConvertRefTree(this).execute();
		} catch (IOException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	@Override
	public void deleteRef(final Ref ref) throws IOException {
		new DeleteBranch(this
	}

	@Override
	public Ref getRef(final String ref) {
		return new GetRef(git.getRepository()
	}

	@Override
	public void push(final CredentialsProvider credentialsProvider
			final boolean force
		new Push(this
	}

	@Override
	public void gc() {
		new GarbageCollector(this).execute();
	}

	@Override
	public RevCommit getCommit(final String commitId) {
		return new GetCommit(this
	}

	@Override
	public RevCommit getLastCommit(final String refName) {
		return retryIfNeeded(RuntimeException.class
	}

	@Override
	public RevCommit getLastCommit(final Ref ref) throws IOException {
		return new GetLastCommit(this
	}

	@Override
	public RevCommit getCommonAncestorCommit(final String branchA
		return new GetCommonAncestorCommit(this
	}

	@Override
	public CommitHistory listCommits(final Ref ref
		return new ListCommits(this
	}

	@Override
	public List<RevCommit> listCommits(final String startCommitId
		return listCommits(new GetCommit(this
	}

	@Override
	public List<RevCommit> listCommits(final ObjectId startRange
		return retryIfNeeded(RuntimeException.class
				() -> new ListCommits(this
	}

	@Override
	public Repository getRepository() {
		return git.getRepository();
	}

	public DeleteBranchCommand _branchDelete() {
		return git.branchDelete();
	}

	public ListBranchCommand _branchList() {
		return git.branchList();
	}

	public CreateBranchCommand _branchCreate() {
		return git.branchCreate();
	}

	public FetchCommand _fetch() {
		return git.fetch();
	}

	public GarbageCollectCommand _gc() {
		return git.gc();
	}

	public PushCommand _push() {
		return git.push();
	}

	@Override
	public ObjectId getTreeFromRef(final String treeRef) {
		return new GetTreeFromRef(this
	}

	@Override
	public void fetch(final CredentialsProvider credential
			final Collection<RefSpec> refSpecs) throws InvalidRemoteException {
		new Fetch(this
	}

	@Override
	public void syncRemote(final Map.Entry<String
		new SyncRemote(this
	}

	@Override
	public List<String> merge(final String source
		return new Merge(this
	}

	@Override
	public List<String> merge(final String source
			throws IOException {
		return new Merge(this
	}

	@Override
	public boolean revertMerge(final String source
			final String mergeCommitId) {
		return new RevertMerge(this
	}

	@Override
	public void cherryPick(final JGitPathImpl target
		new CherryPick(this
	}

	@Override
	public void cherryPick(final String targetBranch
		new CherryPick(this
	}

	@Override
	public void createRef(final String source
		new CreateBranch(this
	}

	@Override
	public List<FileDiff> diffRefs(final String branchA
		return new DiffBranches(this
	}

	@Override
	public List<TextualDiff> textualDiffRefs(final String branchA
		return new TextualDiffBranches(this
	}

	@Override
	public List<TextualDiff> textualDiffRefs(final String branchA
			final String commitIdBranchB) {
		return new TextualDiffBranches(this
	}

	@Override
	public List<String> conflictBranchesChecker(final String branchA
		return new ConflictBranchesChecker(this
	}

	@Override
	public void squash(final String branch
		new Squash(this
	}

	public LogCommand _log() {
		return git.log();
	}

	@Override
	public boolean commit(final String branchName
			final ObjectId originId
		return new Commit(this
	}

	@Override
	public List<DiffEntry> listDiffs(final String startCommitId
		return listDiffs(getCommit(startCommitId).getTree()
	}

	@Override
	public List<DiffEntry> listDiffs(final ObjectId refA
		return new ListDiffs(this
	}

	@Override
	public Map<String
		return new MapDiffContent(this
	}

	@Override
	public InputStream blobAsInputStream(final String treeRef
		return retryIfNeeded(NoSuchFileException.class
				() -> new BlobAsInputStream(this
	}

	@Override
	public RevCommit getFirstCommit(final Ref ref) throws IOException {
		return new GetFirstCommit(this
	}

	@Override
	public List<Ref> listRefs() {
		return new ListRefs(git.getRepository()).execute();
	}

	@Override
	public List<ObjectId> resolveObjectIds(final String... commits) {
		return new ResolveObjectIds(this
	}

	@Override
	public RevCommit resolveRevCommit(final ObjectId objectId) throws IOException {
		return new ResolveRevCommit(git.getRepository()
	}

	@Override
	public List<RefSpec> updateRemoteConfig(final Map.Entry<String
			throws IOException
		return new UpdateRemoteConfig(this
	}

	public AddCommand _add() {
		return git.add();
	}

	public CommitCommand _commit() {
		return git.commit();
	}

	public RemoteListCommand _remoteList() {
		return git.remoteList();
	}

	public static CloneCommand _cloneRepository() {
		return org.eclipse.jgit.api.Git.cloneRepository();
	}

	@Override
	public PathInfo getPathInfo(final String branchName
		return retryIfNeeded(RuntimeException.class
	}

	@Override
	public List<PathInfo> listPathContent(final String branchName
		return retryIfNeeded(RuntimeException.class
	}

	@Override
	public boolean isHEADInitialized() {
		return isHeadInitialized.get();
	}

	@Override
	public void setHeadAsInitialized() {
		isHeadInitialized.set(true);
	}

	@Override
	public void refUpdate(final String branch
			throws IOException
		if (getRepository().getRefDatabase() instanceof RefTreeDatabase) {
			new RefTreeUpdateCommand(this
		} else {
			new SimpleRefUpdateCommand(this
		}
	}

	@Override
	public KetchLeader getKetchLeader() {
		try {
			return leaders.get(getRepository());
		} catch (URISyntaxException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public boolean isKetchEnabled() {
		return isEnabled;
	}

	@Override
	public void enableKetch() {
		isEnabled = true;
	}

	@Override
	public void updateRepo(final Repository repo) {
		this.git = new org.eclipse.jgit.api.Git(repo);
	}

	@Override
	public void updateLeaders(final KetchLeaderCache leaders) {
		this.leaders = leaders;
	}

	static void setRetryTimes(int retryTimes) {
		JGIT_RETRY_TIMES = retryTimes;
	}

	public static <E extends Throwable
			throws E {
		int i = 0;
		do {
			try {
				return supplier.get();
			} catch (final Throwable ex) {
				if (i < (JGIT_RETRY_TIMES - 1)) {
					try {
						Thread.sleep(JGIT_RETRY_SLEEP_TIME);
					} catch (final InterruptedException ignored) {
					}
					LOG.debug(String.format("Unexpected exception (%d/%d)."
				} else {
					LOG.error(String.format("Unexpected exception (%d/%d)."
					if (ex.getClass().isAssignableFrom(eclazz)) {
						throw (E) ex;
					}
					throw new RuntimeException(ex);
				}
			}

			i++;
		} while (i < JGIT_RETRY_TIMES);

		return null;
	}
