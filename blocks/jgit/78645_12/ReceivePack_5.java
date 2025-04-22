	private PostReceiveHook postReceive;

	private boolean reportStatus;

	private boolean echoCommandFailures;

	public PushCertificate getPushCertificate() {
		return pushCert;
	}

	public void setPushCertificate(PushCertificate cert) {
		pushCert = cert;
	}

	public ReceivePack(final Repository into) {
		db = into;
		walk = new RevWalk(db);

		TransferConfig tc = db.getConfig().get(TransferConfig.KEY);
		objectChecker = tc.newReceiveObjectChecker();

		ReceiveConfig rc = db.getConfig().get(ReceiveConfig.KEY);
		allowCreates = rc.allowCreates;
		allowAnyDeletes = true;
		allowBranchDeletes = rc.allowDeletes;
		allowNonFastForwards = rc.allowNonFastForwards;
		allowOfsDelta = rc.allowOfsDelta;
		allowPushOptions = rc.allowPushOptions;
		advertiseRefsHook = AdvertiseRefsHook.DEFAULT;
		refFilter = RefFilter.DEFAULT;
		advertisedHaves = new HashSet<ObjectId>();
		clientShallowCommits = new HashSet<ObjectId>();
		signedPushConfig = rc.signedPush;
		preReceive = PreReceiveHook.NULL;
		postReceive = PostReceiveHook.NULL;
	}

	private static class ReceiveConfig {
		static final SectionParser<ReceiveConfig> KEY = new SectionParser<ReceiveConfig>() {
			public ReceiveConfig parse(final Config cfg) {
				return new ReceiveConfig(cfg);
			}
		};

		final boolean allowCreates;
		final boolean allowDeletes;
		final boolean allowNonFastForwards;
		final boolean allowOfsDelta;
		final boolean allowPushOptions;

		final SignedPushConfig signedPush;

		ReceiveConfig(final Config config) {
			allowCreates = true;
			allowDeletes = !config.getBoolean("receive"
			allowNonFastForwards = !config.getBoolean("receive"
					"denynonfastforwards"
			allowOfsDelta = config.getBoolean("repack"
					true);
			allowPushOptions = config.getBoolean("receive"
					false);
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



	public final Repository getRepository() {
		return db;
	}

	public final RevWalk getRevWalk() {
		return walk;
	}

	public final Map<String
		return refs;
	}

	public void setAdvertisedRefs(Map<String
		refs = allRefs != null ? allRefs : db.getAllRefs();
		refs = refFilter.filter(refs);

		Ref head = refs.get(Constants.HEAD);
		if (head != null && head.isSymbolic())
			refs.remove(Constants.HEAD);

		for (Ref ref : refs.values()) {
			if (ref.getObjectId() != null)
				advertisedHaves.add(ref.getObjectId());
		}
		if (additionalHaves != null)
			advertisedHaves.addAll(additionalHaves);
		else
			advertisedHaves.addAll(db.getAdditionalHaves());
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

	public void setBiDirectionalPipe(final boolean twoWay) {
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

	public void setCheckReceivedObjects(final boolean check) {
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

	public void setAllowCreates(final boolean canCreate) {
		allowCreates = canCreate;
	}

	public boolean isAllowDeletes() {
		return allowAnyDeletes;
	}

	public void setAllowDeletes(final boolean canDelete) {
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

	public void setAllowNonFastForwards(final boolean canRewind) {
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

	public void setRefLogIdent(final PersonIdent pi) {
		refLogIdent = pi;
	}

	public AdvertiseRefsHook getAdvertiseRefsHook() {
		return advertiseRefsHook;
	}

	public RefFilter getRefFilter() {
		return refFilter;
	}

	public void setAdvertiseRefsHook(final AdvertiseRefsHook advertiseRefsHook) {
		if (advertiseRefsHook != null)
			this.advertiseRefsHook = advertiseRefsHook;
		else
			this.advertiseRefsHook = AdvertiseRefsHook.DEFAULT;
	}

	public void setRefFilter(final RefFilter refFilter) {
		this.refFilter = refFilter != null ? refFilter : RefFilter.DEFAULT;
	}

	public int getTimeout() {
		return timeout;
	}

	public void setTimeout(final int seconds) {
		timeout = seconds;
	}

	public void setMaxObjectSizeLimit(final long limit) {
		maxObjectSizeLimit = limit;
	}


	public void setMaxPackSizeLimit(final long limit) {
		if (limit < 0)
			throw new IllegalArgumentException(MessageFormat.format(
					JGitText.get().receivePackInvalidLimit
		maxPackSizeLimit = limit;
	}

	public boolean isSideBand() throws RequestNotYetReadException {
		if (enabledCapabilities == null)
			throw new RequestNotYetReadException();
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
		if (enabledCapabilities == null)
			throw new RequestNotYetReadException();
		return quiet;
	}

	@Nullable
	public List<String> getPushOptions() {
		if (!allowPushOptions) {
			throw new IllegalStateException();
		}
		if (enabledCapabilities == null) {
			throw new RequestNotYetReadException();
		}
		if (pushOptions == null) {
			return null;
		}
		return Collections.unmodifiableList(pushOptions);
	}

	public void setSignedPushConfig(SignedPushConfig cfg) {
		signedPushConfig = cfg;
	}

	private PushCertificateParser getPushCertificateParser() {
		if (pushCertificateParser == null) {
			pushCertificateParser = new PushCertificateParser(db
		}
		return pushCertificateParser;
	}

	public String getPeerUserAgent() {
		return UserAgent.getAgent(enabledCapabilities
	}

	public List<ReceiveCommand> getAllCommands() {
		return Collections.unmodifiableList(commands);
	}

	public void sendError(final String what) {
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

	public void sendMessage(final String what) {
	}

	public OutputStream getMessageOutputStream() {
		return msgOutWrapper;
	}

	public long getPackSize() {
		if (packSize != null)
			return packSize.longValue();
		throw new IllegalStateException(JGitText.get().packSizeNotSetYet);
	}

	private Set<ObjectId> getClientShallowCommits() {
		return clientShallowCommits;
	}

	private boolean hasCommands() {
		return !commands.isEmpty();
	}

	private boolean hasError() {
		return advertiseError != null;
	}

	private void init(final InputStream input
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
