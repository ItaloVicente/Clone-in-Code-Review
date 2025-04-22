public class ReceivePack {
	/** Data in the first line of a request, the line itself plus capabilities. */
	public static class FirstLine {
		private final String line;
		private final Set<String> capabilities;

		/**
		 * Parse the first line of a receive-pack request.
		 *
		 * @param line
		 *            line from the client.
		 */
		public FirstLine(String line) {
			final HashSet<String> caps = new HashSet<String>();
			final int nul = line.indexOf('\0');
			if (nul >= 0) {
				for (String c : line.substring(nul + 1).split(" "))
					caps.add(c);
				this.line = line.substring(0, nul);
			} else
				this.line = line;
			this.capabilities = Collections.unmodifiableSet(caps);
		}

		/** @return non-capabilities part of the line. */
		public String getLine() {
			return line;
		}

		/** @return capabilities parsed from the line. */
		public Set<String> getCapabilities() {
			return capabilities;
		}
	}

	/** Database we write the stored objects into. */
	private final Repository db;

	/** Revision traversal support over {@link #db}. */
	private final RevWalk walk;

	/**
	 * Is the client connection a bi-directional socket or pipe?
	 * <p>
	 * If true, this class assumes it can perform multiple read and write cycles
	 * with the client over the input and output streams. This matches the
	 * functionality available with a standard TCP/IP connection, or a local
	 * operating system or in-memory pipe.
	 * <p>
	 * If false, this class runs in a read everything then output results mode,
	 * making it suitable for single round-trip systems RPCs such as HTTP.
	 */
	private boolean biDirectionalPipe = true;

	/** Should an incoming transfer validate objects? */
	private boolean checkReceivedObjects;

	/** Should an incoming transfer permit create requests? */
	private boolean allowCreates;

	/** Should an incoming transfer permit delete requests? */
	private boolean allowDeletes;

	/** Should an incoming transfer permit non-fast-forward requests? */
	private boolean allowNonFastForwards;

	private boolean allowOfsDelta;

	/** Identity to record action as within the reflog. */
	private PersonIdent refLogIdent;

	/** Hook used while advertising the refs to the client. */
	private AdvertiseRefsHook advertiseRefsHook;

	/** Filter used while advertising the refs to the client. */
	private RefFilter refFilter;

