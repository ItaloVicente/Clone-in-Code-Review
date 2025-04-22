		advertisedHaves = new HashSet<ObjectId>();
	}

	private static class ReceiveConfig {
		static final SectionParser<ReceiveConfig> KEY = new SectionParser<ReceiveConfig>() {
			public ReceiveConfig parse(final Config cfg) {
				return new ReceiveConfig(cfg);
			}
		};

		final boolean checkReceivedObjects;

		final boolean allowCreates;

		final boolean allowDeletes;

		final boolean allowNonFastForwards;

		final boolean allowOfsDelta;

		ReceiveConfig(final Config config) {
			checkReceivedObjects = config.getBoolean("receive", "fsckobjects",
					false);
			allowCreates = true;
			allowDeletes = !config.getBoolean("receive", "denydeletes", false);
			allowNonFastForwards = !config.getBoolean("receive",
					"denynonfastforwards", false);
			allowOfsDelta = config.getBoolean("repack", "usedeltabaseoffset",
					true);
		}
	}

	/**
	 * Output stream that wraps the current {@link #msgOut}.
	 * <p>
	 * We don't want to expose {@link #msgOut} directly because it can change
	 * several times over the course of a session.
	 */
	private class MessageOutputWrapper extends OutputStream {
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

	/** @return the repository this receive completes into. */
	public final Repository getRepository() {
		return db;
	}

	/** @return the RevWalk instance used by this connection. */
	public final RevWalk getRevWalk() {
		return walk;
	}

	/**
	 * Get refs which were advertised to the client.
	 *
	 * @return all refs which were advertised to the client, or null if
	 *         {@link #setAdvertisedRefs(Map, Set)} has not been called yet.
	 */
	public final Map<String, Ref> getAdvertisedRefs() {
		return refs;
	}

	/**
	 * Set the refs advertised by this ReceivePack.
	 * <p>
	 * Intended to be called from a {@link PreReceiveHook}.
	 *
	 * @param allRefs
	 *            explicit set of references to claim as advertised by this
	 *            ReceivePack instance. This overrides any references that
	 *            may exist in the source repository. The map is passed
	 *            to the configured {@link #getRefFilter()}. If null, assumes
	 *            all refs were advertised.
	 * @param additionalHaves
	 *            explicit set of additional haves to claim as advertised. If
	 *            null, assumes the default set of additional haves from the
	 *            repository.
	 */
	public void setAdvertisedRefs(Map<String, Ref> allRefs,
			Set<ObjectId> additionalHaves) {
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

	/**
	 * Get objects advertised to the client.
	 *
	 * @return the set of objects advertised to the as present in this repository,
	 *         or null if {@link #setAdvertisedRefs(Map, Set)} has not been called
	 *         yet.
	 */
	public final Set<ObjectId> getAdvertisedObjects() {
		return advertisedHaves;
	}

	/**
	 * @return true if this instance will validate all referenced, but not
	 *         supplied by the client, objects are reachable from another
	 *         reference.
	 */
	public boolean isCheckReferencedObjectsAreReachable() {
		return checkReferencedIsReachable;
	}

	/**
	 * Validate all referenced but not supplied objects are reachable.
	 * <p>
	 * If enabled, this instance will verify that references to objects not
	 * contained within the received pack are already reachable through at least
	 * one other reference displayed as part of {@link #getAdvertisedRefs()}.
	 * <p>
	 * This feature is useful when the application doesn't trust the client to
	 * not provide a forged SHA-1 reference to an object, in an attempt to
	 * access parts of the DAG that they aren't allowed to see and which have
	 * been hidden from them via the configured {@link AdvertiseRefsHook} or
	 * {@link RefFilter}.
	 * <p>
	 * Enabling this feature may imply at least some, if not all, of the same
	 * functionality performed by {@link #setCheckReceivedObjects(boolean)}.
	 * Applications are encouraged to enable both features, if desired.
	 *
	 * @param b
	 *            {@code true} to enable the additional check.
	 */
	public void setCheckReferencedObjectsAreReachable(boolean b) {
		this.checkReferencedIsReachable = b;
	}

	/**
	 * @return true if this class expects a bi-directional pipe opened between
	 *         the client and itself. The default is true.
	 */
	public boolean isBiDirectionalPipe() {
		return biDirectionalPipe;
	}

	/**
	 * @param twoWay
	 *            if true, this class will assume the socket is a fully
	 *            bidirectional pipe between the two peers and takes advantage
	 *            of that by first transmitting the known refs, then waiting to
	 *            read commands. If false, this class assumes it must read the
	 *            commands before writing output and does not perform the
	 *            initial advertising.
	 */
	public void setBiDirectionalPipe(final boolean twoWay) {
		biDirectionalPipe = twoWay;
	}

	/**
	 * @return true if this instance will verify received objects are formatted
	 *         correctly. Validating objects requires more CPU time on this side
	 *         of the connection.
	 */
	public boolean isCheckReceivedObjects() {
		return checkReceivedObjects;
	}

	/**
	 * @param check
	 *            true to enable checking received objects; false to assume all
	 *            received objects are valid.
	 */
	public void setCheckReceivedObjects(final boolean check) {
		checkReceivedObjects = check;
	}

	/** @return true if the client can request refs to be created. */
	public boolean isAllowCreates() {
		return allowCreates;
	}

	/**
	 * @param canCreate
	 *            true to permit create ref commands to be processed.
	 */
	public void setAllowCreates(final boolean canCreate) {
		allowCreates = canCreate;
	}

	/** @return true if the client can request refs to be deleted. */
	public boolean isAllowDeletes() {
		return allowDeletes;
	}

	/**
	 * @param canDelete
	 *            true to permit delete ref commands to be processed.
	 */
	public void setAllowDeletes(final boolean canDelete) {
		allowDeletes = canDelete;
	}

	/**
	 * @return true if the client can request non-fast-forward updates of a ref,
	 *         possibly making objects unreachable.
	 */
	public boolean isAllowNonFastForwards() {
		return allowNonFastForwards;
	}

	/**
	 * @param canRewind
	 *            true to permit the client to ask for non-fast-forward updates
	 *            of an existing ref.
	 */
	public void setAllowNonFastForwards(final boolean canRewind) {
		allowNonFastForwards = canRewind;
	}

	/** @return identity of the user making the changes in the reflog. */
	public PersonIdent getRefLogIdent() {
		return refLogIdent;
	}

	/**
	 * Set the identity of the user appearing in the affected reflogs.
	 * <p>
	 * The timestamp portion of the identity is ignored. A new identity with the
	 * current timestamp will be created automatically when the updates occur
	 * and the log records are written.
	 *
	 * @param pi
	 *            identity of the user. If null the identity will be
	 *            automatically determined based on the repository
	 *            configuration.
	 */
	public void setRefLogIdent(final PersonIdent pi) {
		refLogIdent = pi;
	}

	/** @return the hook used while advertising the refs to the client */
	public AdvertiseRefsHook getAdvertiseRefsHook() {
		return advertiseRefsHook;
	}

	/** @return the filter used while advertising the refs to the client */
	public RefFilter getRefFilter() {
		return refFilter;
	}

	/**
	 * Set the hook used while advertising the refs to the client.
	 * <p>
	 * If the {@link AdvertiseRefsHook} chooses to call
	 * {@link #setAdvertisedRefs(Map,Set)}, only refs set by this hook
	 * <em>and</em> selected by the {@link RefFilter} will be shown to the client.
	 * Clients may still attempt to create or update a reference not advertised by
	 * the configured {@link AdvertiseRefsHook}. These attempts should be rejected
	 * by a matching {@link PreReceiveHook}.
	 *
	 * @param advertiseRefsHook
	 *            the hook; may be null to show all refs.
	 */
	public void setAdvertiseRefsHook(final AdvertiseRefsHook advertiseRefsHook) {
		if (advertiseRefsHook != null)
			this.advertiseRefsHook = advertiseRefsHook;
		else
			this.advertiseRefsHook = AdvertiseRefsHook.DEFAULT;
	}

	/**
	 * Set the filter used while advertising the refs to the client.
	 * <p>
	 * Only refs allowed by this filter will be shown to the client.
	 * The filter is run against the refs specified by the
	 * {@link AdvertiseRefsHook} (if applicable).
	 *
	 * @param refFilter
	 *            the filter; may be null to show all refs.
	 */
	public void setRefFilter(final RefFilter refFilter) {
		this.refFilter = refFilter != null ? refFilter : RefFilter.DEFAULT;
