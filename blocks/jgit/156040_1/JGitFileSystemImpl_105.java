	private static final Logger LOGGER = LoggerFactory.getLogger(JGitFileSystemImpl.class);
	private static final Set<String> SUPPORTED_ATTR_VIEWS = unmodifiableSet(new HashSet<>(asList("basic"
	private final JGitFileSystemProvider provider;
	private final Git git;
	private final String toStringContent;
	private boolean isClosed = false;
	private final FileStore fileStore;
	private final String name;
	private final CredentialsProvider credential;
	private final Map<FileSystemHooks
	private final AtomicInteger numberOfCommitsSinceLastGC = new AtomicInteger(0);
	private FileSystemState state = FileSystemState.NORMAL;
	private CommitInfo batchCommitInfo = null;
	private Map<Path
	private JGitFileSystemLock lock;
	private JGitFileSystemsEventsManager fsEventsManager;

	private List<WatchEvent<?>> postponedWatchEvents = Collections.synchronizedList(new ArrayList<>());

	public JGitFileSystemImpl(final JGitFileSystemProvider provider
			final Git git
			JGitFileSystemsEventsManager fsEventsManager
		this.fsEventsManager = fsEventsManager;
		this.provider = checkNotNull("provider"
		this.git = checkNotNull("git"
		this.name = checkNotEmpty("name"

		this.lock = checkNotNull("lock"
		this.credential = checkNotNull("credential"
		this.fsHooks = fsHooks;
		this.fileStore = new JGitFileStore(this.git.getRepository());
		if (fullHostNames != null && !fullHostNames.isEmpty()) {
			final StringBuilder sb = new StringBuilder();
			final Iterator<Map.Entry<String
			while (iterator.hasNext()) {
				final Map.Entry<String
				if (iterator.hasNext()) {
					sb.append("\n");
				}
			}
			toStringContent = sb.toString();
		} else {
		}
	}

	@Override
	public String id() {
		return name;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public Git getGit() {
		return git;
	}

	@Override
	public CredentialsProvider getCredential() {
		return credential;
	}

	@Override
	public FileSystemProvider provider() {
		return provider;
	}

	@Override
	public boolean isOpen() {
		return !isClosed;
	}

	@Override
	public boolean isReadOnly() {
		return false;
	}

	@Override
	public String getSeparator() {
		return "/";
	}

	@Override
	public Iterable<Path> getRootDirectories() {
		checkClosed();
		return () -> new Iterator<Path>() {

			Iterator<Ref> branches = null;

			@Override
			public boolean hasNext() {
				if (branches == null) {
					init();
				}
				return branches.hasNext();
			}

			private void init() {
				branches = git.listRefs().iterator();
			}

			@Override
			public Path next() {

				if (branches == null) {
					init();
				}
				try {
					return JGitPathImpl.createRoot(JGitFileSystemImpl.this
							shortenRefName(branches.next().getName()) + "@" + name
				} catch (NoSuchElementException e) {
					throw new IllegalStateException("The gitnio directory is in an invalid state. "
							+ "If you are an IntelliJ IDEA user
							+ "a custom directory for your git repository. "
							+ "You can specify a custom directory using '-D" + GIT_NIO_DIR + "=/tmp/dir'. "
				}
			}

			@Override
			public void remove() {
				throw new UnsupportedOperationException();
			}
		};
	}

	@Override
	public Iterable<FileStore> getFileStores() {
		checkClosed();
		return () -> new Iterator<FileStore>() {

			private int i = 0;

			@Override
			public boolean hasNext() {
				return i < 1;
			}

			@Override
			public FileStore next() {
				if (i < 1) {
					i++;
					return fileStore;
				} else {
					throw new NoSuchElementException();
				}
			}

			@Override
			public void remove() {
				throw new UnsupportedOperationException();
			}
		};
	}

	@Override
	public Set<String> supportedFileAttributeViews() {
		checkClosed();
		return SUPPORTED_ATTR_VIEWS;
	}

	@Override
	public Path getPath(final String first
		checkClosed();
		if (first == null || first.trim().isEmpty()) {
			return new JGitFSPath(this);
		}

		if (more == null || more.length == 0) {
			return JGitPathImpl.create(this
		}

		final StringBuilder sb = new StringBuilder();
		for (final String segment : more) {
			if (segment.length() > 0) {
				if (sb.length() > 0) {
					sb.append(getSeparator());
				}
				sb.append(segment);
			}
		}
		return JGitPathImpl.create(this
	}

	@Override
	public PathMatcher getPathMatcher(final String syntaxAndPattern)
			throws IllegalArgumentException
		checkClosed();
		checkNotEmpty("syntaxAndPattern"
		throw new UnsupportedOperationException();
	}

	@Override
	public UserPrincipalLookupService getUserPrincipalLookupService() throws UnsupportedOperationException {
		checkClosed();
		throw new UnsupportedOperationException();
	}

	@Override
	public WatchService newWatchService() throws UnsupportedOperationException
		checkClosed();
		return fsEventsManager.newWatchService(name);
	}

	@Override
	public void close() throws IOException {
		if (isClosed) {
			return;
		}
		git.getRepository().close();
		isClosed = true;
		try {
			fsEventsManager.close(name);
		} catch (final Exception ex) {
			LOGGER.error("Error during close of WatchServices [" + toString() + "]"
		} finally {
			provider.onCloseFileSystem(this);
		}
	}

	@Override
	public void checkClosed() throws IllegalStateException {
		if (isClosed) {
			throw new IllegalStateException("FileSystem is closed.");
		}
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			if (o != null && o instanceof JGitFileSystemProxy) {
				o = ((JGitFileSystemProxy) o).getRealJGitFileSystem();
			} else {
				return false;
			}
		}

		JGitFileSystemImpl that = (JGitFileSystemImpl) o;

		if (!name.equals(that.name)) {
			return false;
		}

		return true;
	}

	@Override
	public String toString() {
		return toStringContent;
	}

	@Override
	public int hashCode() {
		int result = name.hashCode();
		return result;
	}

	@Override
	public void publishEvents(final Path watchableRoot
		fsEventsManager.publishEvents(name
	}

	@Override
	public void dispose() throws IOException {
		if (!isClosed) {
			close();
		}
		provider.onDisposeFileSystem(this);
	}

	@Override
	public boolean isOnBatch() {
		return state.equals(FileSystemState.BATCH);
	}

	@Override
	public void setState(String state) {
		try {
			this.state = FileSystemState.valueOf(state);
		} catch (final Exception ex) {
			this.state = FileSystemState.NORMAL;
		}
	}

	@Override
	public CommitInfo buildCommitInfo(final String defaultMessage
		String sessionId = null;
		String name = null;
		String email = null;
		String message = defaultMessage;
		TimeZone timeZone = null;
		Date when = null;

		if (op != null) {
			sessionId = op.getSessionId();
			name = op.getName();
			email = op.getEmail();
			if (op.getMessage() != null && !op.getMessage().trim().isEmpty()) {
				message = op.getMessage();
			}
			timeZone = op.getTimeZone();
			when = op.getWhen();
		}

		return new CommitInfo(sessionId
	}

	@Override
	public void setBatchCommitInfo(final String defaultMessage
		this.batchCommitInfo = buildCommitInfo(defaultMessage
	}

	@Override
	public void setHadCommitOnBatchState(final Path path
		final Path root = checkNotNull("path"
		this.hadCommitOnBatchState.put(root.getRoot()
	}

	@Override
	public void setHadCommitOnBatchState(final boolean value) {
		for (Map.Entry<Path
			entry.setValue(value);
		}
	}

	@Override
	public boolean isHadCommitOnBatchState(final Path path) {
		final Path root = checkNotNull("path"
		return hadCommitOnBatchState.containsKey(root) ? hadCommitOnBatchState.get(root) : false;
	}

	@Override
	public void setBatchCommitInfo(CommitInfo batchCommitInfo) {
		this.batchCommitInfo = batchCommitInfo;
	}

	@Override
	public CommitInfo getBatchCommitInfo() {
		return batchCommitInfo;
	}

	@Override
	public int incrementAndGetCommitCount() {
		return numberOfCommitsSinceLastGC.incrementAndGet();
	}

	@Override
	public void resetCommitCount() {
		numberOfCommitsSinceLastGC.set(0);
	}

	@Override
	public int getNumberOfCommitsSinceLastGC() {
		return numberOfCommitsSinceLastGC.get();
	}

	@Override
	public FileSystemState getState() {
		return state;
	}

	@Override
	public void lock() {
		lock.lock();
	}

	@Override
	public void unlock() {
		lock.unlock();
	}

	public JGitFileSystemLock getLock() {
		return lock;
	}

	@Override
	public void addPostponedWatchEvents(List<WatchEvent<?>> postponedWatchEvents) {
		this.postponedWatchEvents.addAll(postponedWatchEvents);
	}

	@Override
	public List<WatchEvent<?>> getPostponedWatchEvents() {
		return postponedWatchEvents;
	}

	@Override
	public void clearPostponedWatchEvents() {
		this.postponedWatchEvents = Collections.synchronizedList(new ArrayList<>());
	}

	@Override
	public boolean hasPostponedEvents() {
		return !getPostponedWatchEvents().isEmpty();
	}

	@Override
	public boolean hasBeenInUse() {
		return lock.hasBeenInUse();
	}

	@Override
	public void notifyExternalUpdate() {
		Object hook = fsHooks.get(FileSystemHooks.ExternalUpdate);
		if (hook != null) {
			JGitFSHooks.executeFSHooks(hook
		}
	}

	@Override
	public void notifyPostCommit(int exitCode) {
		Object hook = fsHooks.get(FileSystemHooks.PostCommit);
		if (hook != null) {
			FileSystemHookExecutionContext ctx = new FileSystemHookExecutionContext(name);
			ctx.addParam(FileSystemHooksConstants.POST_COMMIT_EXIT_CODE

			JGitFSHooks.executeFSHooks(hook
		}
	}

	@Override
	public void checkBranchAccess(final ReceiveCommand command
		Object hook = fsHooks.get(FileSystemHooks.BranchAccessCheck);
		if (hook != null) {
			FileSystemHookExecutionContext ctx = new FileSystemHookExecutionContext(name);
			ctx.addParam(FileSystemHooksConstants.RECEIVE_COMMAND
			ctx.addParam(FileSystemHooksConstants.USER

			JGitFSHooks.executeFSHooks(hook
		}
	}

	@Override
	public void filterBranchAccess(final UploadPack uploadPack
		Object hook = fsHooks.get(FileSystemHooks.BranchAccessFilter);
		if (hook != null) {
			FileSystemHookExecutionContext ctx = new FileSystemHookExecutionContext(name);
			ctx.addParam(FileSystemHooksConstants.UPLOAD_PACK
			ctx.addParam(FileSystemHooksConstants.USER

			JGitFSHooks.executeFSHooks(hook
		}
	}
