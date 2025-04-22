	private PostReceiveHook postReceive;

	private boolean reportStatus;

	private boolean usePushOptions;
	private List<String> pushOptions;

	public ReceivePack(Repository into) {
		db = into;
		walk = new RevWalk(db);
		walk.setRetainBody(false);

		TransferConfig tc = db.getConfig().get(TransferConfig.KEY);
		objectChecker = tc.newReceiveObjectChecker();

		ReceiveConfig rc = db.getConfig().get(ReceiveConfig::new);
		allowCreates = rc.allowCreates;
		allowAnyDeletes = true;
		allowBranchDeletes = rc.allowDeletes;
		allowNonFastForwards = rc.allowNonFastForwards;
		allowOfsDelta = rc.allowOfsDelta;
		allowPushOptions = rc.allowPushOptions;
		maxCommandBytes = rc.maxCommandBytes;
		maxDiscardBytes = rc.maxDiscardBytes;
		advertiseRefsHook = AdvertiseRefsHook.DEFAULT;
		refFilter = RefFilter.DEFAULT;
		advertisedHaves = new HashSet<>();
		clientShallowCommits = new HashSet<>();
		signedPushConfig = rc.signedPush;
		preReceive = PreReceiveHook.NULL;
		postReceive = PostReceiveHook.NULL;
	}

	protected static class ReceiveConfig {
		final boolean allowCreates;

		final boolean allowDeletes;

		final boolean allowNonFastForwards;

		final boolean allowOfsDelta;

		final boolean allowPushOptions;

		final long maxCommandBytes;

		final long maxDiscardBytes;

		final SignedPushConfig signedPush;

		ReceiveConfig(Config config) {
			allowCreates = true;
			allowDeletes = !config.getBoolean("receive"
			allowNonFastForwards = !config.getBoolean("receive"
					"denynonfastforwards"
			allowOfsDelta = config.getBoolean("repack"
					true);
			allowPushOptions = config.getBoolean("receive"
					false);
			maxCommandBytes = config.getLong("receive"
					"maxCommandBytes"
					3 << 20);
			maxDiscardBytes = config.getLong("receive"
					"maxCommandDiscardBytes"
					-1);
			signedPush = SignedPushConfig.KEY.parse(config);
		}
	}

	class MessageOutputWrapper extends OutputStream {
		@Override
		public void write(int ch) {
			if (msgOut != null) {
				try {
					msgOut.write(ch);
				} catch (IOException e) {
				}
			}
		}

		@Override
		public void write(byte[] b
			if (msgOut != null) {
				try {
					msgOut.write(b
				} catch (IOException e) {
				}
			}
		}

		@Override
		public void write(byte[] b) {
			write(b
		}

		@Override
		public void flush() {
			if (msgOut != null) {
				try {
					msgOut.flush();
				} catch (IOException e) {
				}
			}
		}
	}

	protected String getLockMessageProcessName() {
	}

	public Repository getRepository() {
		return db;
	}

	public RevWalk getRevWalk() {
		return walk;
	}

	public Map<String
		return refs;
	}

	public void setAdvertisedRefs(Map<String
			Set<ObjectId> additionalHaves) {
		refs = allRefs != null ? allRefs : db.getAllRefs();
		refs = refFilter.filter(refs);
		advertisedHaves.clear();

		Ref head = refs.get(HEAD);
		if (head != null && head.isSymbolic()) {
			refs.remove(HEAD);
		}

		for (Ref ref : refs.values()) {
			if (ref.getObjectId() != null) {
				advertisedHaves.add(ref.getObjectId());
			}
		}
		if (additionalHaves != null) {
			advertisedHaves.addAll(additionalHaves);
		} else {
			advertisedHaves.addAll(db.getAdditionalHaves());
		}
	}

	public final Set<ObjectId> getAdvertisedObjects() {
		return advertisedHaves;
	}

	public boolean isCheckReferencedObjectsAreReachable() {
		return checkReferencedIsReachable;
	}

	public void setCheckReferencedObjectsAreReachable(boolean b) {
		this.checkReferencedIsReachable = b;
	}

	public boolean isBiDirectionalPipe() {
		return biDirectionalPipe;
	}

	public void setBiDirectionalPipe(boolean twoWay) {
		biDirectionalPipe = twoWay;
	}

	public boolean isExpectDataAfterPackFooter() {
		return expectDataAfterPackFooter;
	}

	public void setExpectDataAfterPackFooter(boolean e) {
		expectDataAfterPackFooter = e;
	}

	public boolean isCheckReceivedObjects() {
		return objectChecker != null;
	}

	public void setCheckReceivedObjects(boolean check) {
		if (check && objectChecker == null)
			setObjectChecker(new ObjectChecker());
		else if (!check && objectChecker != null)
			setObjectChecker(null);
	}

	public void setObjectChecker(ObjectChecker impl) {
		objectChecker = impl;
	}

	public boolean isAllowCreates() {
		return allowCreates;
	}

	public void setAllowCreates(boolean canCreate) {
		allowCreates = canCreate;
	}

	public boolean isAllowDeletes() {
		return allowAnyDeletes;
	}

	public void setAllowDeletes(boolean canDelete) {
		allowAnyDeletes = canDelete;
	}

	public boolean isAllowBranchDeletes() {
		return allowBranchDeletes;
	}

	public void setAllowBranchDeletes(boolean canDelete) {
		allowBranchDeletes = canDelete;
	}

	public boolean isAllowNonFastForwards() {
		return allowNonFastForwards;
	}

	public void setAllowNonFastForwards(boolean canRewind) {
		allowNonFastForwards = canRewind;
	}

	public boolean isAtomic() {
		return atomic;
	}

	public void setAtomic(boolean atomic) {
		this.atomic = atomic;
	}

	public PersonIdent getRefLogIdent() {
		return refLogIdent;
	}

	public void setRefLogIdent(PersonIdent pi) {
		refLogIdent = pi;
	}

	public AdvertiseRefsHook getAdvertiseRefsHook() {
		return advertiseRefsHook;
	}

	public RefFilter getRefFilter() {
		return refFilter;
	}

	public void setAdvertiseRefsHook(AdvertiseRefsHook advertiseRefsHook) {
		if (advertiseRefsHook != null)
			this.advertiseRefsHook = advertiseRefsHook;
		else
			this.advertiseRefsHook = AdvertiseRefsHook.DEFAULT;
	}

	public void setRefFilter(RefFilter refFilter) {
		this.refFilter = refFilter != null ? refFilter : RefFilter.DEFAULT;
	}

	public int getTimeout() {
		return timeout;
	}

	public void setTimeout(int seconds) {
		timeout = seconds;
	}

	public void setMaxCommandBytes(long limit) {
		maxCommandBytes = limit;
	}

	public void setMaxCommandDiscardBytes(long limit) {
		maxDiscardBytes = limit;
	}

	public void setMaxObjectSizeLimit(long limit) {
		maxObjectSizeLimit = limit;
	}

	public void setMaxPackSizeLimit(long limit) {
		if (limit < 0)
			throw new IllegalArgumentException(
					MessageFormat.format(JGitText.get().receivePackInvalidLimit
							Long.valueOf(limit)));
		maxPackSizeLimit = limit;
	}

	public boolean isSideBand() throws RequestNotYetReadException {
		checkRequestWasRead();
		return enabledCapabilities.contains(CAPABILITY_SIDE_BAND_64K);
	}

	public boolean isAllowQuiet() {
		return allowQuiet;
	}

	public void setAllowQuiet(boolean allow) {
		allowQuiet = allow;
	}

	public boolean isAllowPushOptions() {
		return allowPushOptions;
	}

	public void setAllowPushOptions(boolean allow) {
		allowPushOptions = allow;
	}

	public boolean isQuiet() throws RequestNotYetReadException {
		checkRequestWasRead();
		return quiet;
	}

	public void setSignedPushConfig(SignedPushConfig cfg) {
		signedPushConfig = cfg;
	}

	private PushCertificateParser getPushCertificateParser() {
		if (pushCertificateParser == null) {
			pushCertificateParser = new PushCertificateParser(db
					signedPushConfig);
		}
		return pushCertificateParser;
	}

	public String getPeerUserAgent() {
		return UserAgent.getAgent(enabledCapabilities
	}

	public List<ReceiveCommand> getAllCommands() {
		return Collections.unmodifiableList(commands);
	}

	public void sendError(String what) {
		if (refs == null) {
			if (advertiseError == null)
				advertiseError = new StringBuilder();
			advertiseError.append(what).append('\n');
		} else {
		}
	}

	private void fatalError(String msg) {
		if (errOut != null) {
			try {
				errOut.write(Constants.encode(msg));
				errOut.flush();
			} catch (IOException e) {
			}
		} else {
			sendError(msg);
		}
	}

	public void sendMessage(String what) {
	}

	public OutputStream getMessageOutputStream() {
		return msgOutWrapper;
	}

	public long getPackSize() {
		if (packSize != null)
			return packSize.longValue();
		throw new IllegalStateException(JGitText.get().packSizeNotSetYet);
	}

	protected Set<ObjectId> getClientShallowCommits() {
		return clientShallowCommits;
	}

	protected boolean hasCommands() {
		return !commands.isEmpty();
	}

	protected boolean hasError() {
		return advertiseError != null;
	}

	protected void init(final InputStream input
			final OutputStream messages) {
		origOut = output;
		rawIn = input;
		rawOut = output;
		msgOut = messages;

		if (timeout > 0) {
			final Thread caller = Thread.currentThread();
			timeoutIn = new TimeoutInputStream(rawIn
			TimeoutOutputStream o = new TimeoutOutputStream(rawOut
			timeoutIn.setTimeout(timeout * 1000);
			o.setTimeout(timeout * 1000);
			rawIn = timeoutIn;
			rawOut = o;
		}

		pckIn = new PacketLineIn(rawIn);
		pckOut = new PacketLineOut(rawOut);
		pckOut.setFlushOnEnd(false);

		enabledCapabilities = new HashSet<>();
		commands = new ArrayList<>();
	}

	protected Map<String
		if (refs == null)
			setAdvertisedRefs(null
		return refs;
	}

	protected void receivePackAndCheckConnectivity() throws IOException {
		receivePack();
		if (needCheckConnectivity()) {
			checkSubmodules();
			checkConnectivity();
		}
		parser = null;
	}

	protected void unlockPack() throws IOException {
		if (packLock != null) {
			packLock.unlock();
			packLock = null;
		}
	}

	public void sendAdvertisedRefs(RefAdvertiser adv)
			throws IOException
		if (advertiseError != null) {
			return;
		}

		try {
			advertiseRefsHook.advertiseRefs(this);
		} catch (ServiceMayNotContinueException fail) {
			if (fail.getMessage() != null) {
				fail.setOutput();
			}
			throw fail;
		}

		adv.init(db);
		adv.advertiseCapability(CAPABILITY_SIDE_BAND_64K);
		adv.advertiseCapability(CAPABILITY_DELETE_REFS);
		adv.advertiseCapability(CAPABILITY_REPORT_STATUS);
		if (allowQuiet)
			adv.advertiseCapability(CAPABILITY_QUIET);
		String nonce = getPushCertificateParser().getAdvertiseNonce();
		if (nonce != null) {
			adv.advertiseCapability(nonce);
		}
		if (db.getRefDatabase().performsAtomicTransactions())
			adv.advertiseCapability(CAPABILITY_ATOMIC);
		if (allowOfsDelta)
			adv.advertiseCapability(CAPABILITY_OFS_DELTA);
		if (allowPushOptions) {
			adv.advertiseCapability(CAPABILITY_PUSH_OPTIONS);
		}
		adv.advertiseCapability(OPTION_AGENT
		adv.send(getAdvertisedOrDefaultRefs().values());
		for (ObjectId obj : advertisedHaves)
			adv.advertiseHave(obj);
		if (adv.isEmpty())
			adv.advertiseId(ObjectId.zeroId()
		adv.end();
	}

	@Nullable
	public ReceivedPackStatistics getReceivedPackStatistics() {
		return stats;
	}

	protected void recvCommands() throws IOException {
		PacketLineIn pck = maxCommandBytes > 0
				? new PacketLineIn(rawIn
				: pckIn;
		PushCertificateParser certParser = getPushCertificateParser();
		boolean firstPkt = true;
		try {
			for (;;) {
				String line;
				try {
					line = pck.readString();
				} catch (EOFException eof) {
					if (commands.isEmpty())
						return;
					throw eof;
				}
				if (PacketLineIn.isEnd(line)) {
					break;
				}

					parseShallow(line.substring(8
					continue;
				}

				if (firstPkt) {
					firstPkt = false;
					FirstCommand firstLine = FirstCommand.fromLine(line);
					enabledCapabilities = firstLine.getCapabilities();
					line = firstLine.getLine();
					enableCapabilities();

					if (line.equals(GitProtocolConstants.OPTION_PUSH_CERT)) {
						certParser.receiveHeader(pck
						continue;
					}
				}

				if (line.equals(PushCertificateParser.BEGIN_SIGNATURE)) {
					certParser.receiveSignature(pck);
					continue;
				}
