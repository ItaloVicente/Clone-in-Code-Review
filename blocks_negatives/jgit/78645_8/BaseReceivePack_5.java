	public void setPushCertificate(PushCertificate cert) {
		pushCert = cert;
	}

	/**
	 * Create a new pack receive for an open repository.
	 *
	 * @param into
	 *            the destination repository.
	 */
	protected BaseReceivePack(final Repository into) {
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
	}

	/** Configuration for receive operations. */
	protected static class ReceiveConfig {
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
			allowDeletes = !config.getBoolean("receive", "denydeletes", false); //$NON-NLS-1$ //$NON-NLS-2$
			allowNonFastForwards = !config.getBoolean("receive", //$NON-NLS-1$
					"denynonfastforwards", false); //$NON-NLS-1$
			allowOfsDelta = config.getBoolean("repack", "usedeltabaseoffset", //$NON-NLS-1$ //$NON-NLS-2$
					true);
			allowPushOptions = config.getBoolean("receive", "pushoptions", //$NON-NLS-1$ //$NON-NLS-2$
					false);
			signedPush = SignedPushConfig.KEY.parse(config);
		}
	}

	/**
	 * Output stream that wraps the current {@link #msgOut}.
	 * <p>
	 * We don't want to expose {@link #msgOut} directly because it can change
	 * several times over the course of a session.
	 */
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
		public void write(byte[] b, int off, int len) {
			if (msgOut != null) {
				try {
					msgOut.write(b, off, len);
				} catch (IOException e) {
				}
			}
		}

		@Override
		public void write(byte[] b) {
			write(b, 0, b.length);
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

	/** @return the process name used for pack lock messages. */
	protected abstract String getLockMessageProcessName();
