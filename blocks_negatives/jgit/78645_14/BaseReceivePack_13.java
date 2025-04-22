	public long getPackSize() {
		if (packSize != null)
			return packSize.longValue();
		throw new IllegalStateException(JGitText.get().packSizeNotSetYet);
	}

	/**
	 * Get the commits from the client's shallow file.
	 *
	 * @return if the client is a shallow repository, the list of edge commits
	 *     that define the client's shallow boundary. Empty set if the client
	 *     is earlier than Git 1.9, or is a full clone.
	 * @since 3.5
	 */
	protected Set<ObjectId> getClientShallowCommits() {
		return clientShallowCommits;
	}

	/** @return true if any commands to be executed have been read. */
	protected boolean hasCommands() {
		return !commands.isEmpty();
	}

	/** @return true if an error occurred that should be advertised. */
	protected boolean hasError() {
		return advertiseError != null;
	}

	/**
	 * Initialize the instance with the given streams.
	 *
	 * @param input
	 *            raw input to read client commands and pack data from. Caller
	 *            must ensure the input is buffered, otherwise read performance
	 *            may suffer.
	 * @param output
	 *            response back to the Git network client. Caller must ensure
	 *            the output is buffered, otherwise write performance may
	 *            suffer.
	 * @param messages
	 *            secondary "notice" channel to send additional messages out
	 *            through. When run over SSH this should be tied back to the
	 *            standard error channel of the command execution. For most
	 *            other network connections this should be null.
	 */
	protected void init(final InputStream input, final OutputStream output,
			final OutputStream messages) {
		origOut = output;
		rawIn = input;
		rawOut = output;
		msgOut = messages;

		if (timeout > 0) {
			final Thread caller = Thread.currentThread();
			timeoutIn = new TimeoutInputStream(rawIn, timer);
			TimeoutOutputStream o = new TimeoutOutputStream(rawOut, timer);
			timeoutIn.setTimeout(timeout * 1000);
			o.setTimeout(timeout * 1000);
			rawIn = timeoutIn;
			rawOut = o;
		}

		if (maxPackSizeLimit >= 0)
			rawIn = new LimitedInputStream(rawIn, maxPackSizeLimit) {
				@Override
				protected void limitExceeded() throws TooLargePackException {
					throw new TooLargePackException(limit);
				}
			};

		pckIn = new PacketLineIn(rawIn);
		pckOut = new PacketLineOut(rawOut);
		pckOut.setFlushOnEnd(false);

		enabledCapabilities = new HashSet<String>();
		commands = new ArrayList<ReceiveCommand>();
	}

	/** @return advertised refs, or the default if not explicitly advertised. */
	protected Map<String, Ref> getAdvertisedOrDefaultRefs() {
		if (refs == null)
			setAdvertisedRefs(null, null);
		return refs;
	}

	/**
	 * Receive a pack from the stream and check connectivity if necessary.
	 *
	 * @throws IOException
	 *             an error occurred during unpacking or connectivity checking.
	 */
	protected void receivePackAndCheckConnectivity() throws IOException {
		receivePack();
		if (needCheckConnectivity())
			checkConnectivity();
		parser = null;
	}

	/**
	 * Unlock the pack written by this object.
	 *
	 * @throws IOException
	 *             the pack could not be unlocked.
	 */
	protected void unlockPack() throws IOException {
		if (packLock != null) {
			packLock.unlock();
			packLock = null;
		}
	}
