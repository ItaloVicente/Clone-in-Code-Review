	/**
	 * Commit time of the newest objects the client has asked us using
	 * --shallow-since not to send. Cannot be nonzero if depth is nonzero.
	 */
	private int shallowSince;

	/**
	 * (Possibly short) ref names, ancestors of which the client has asked us
	 * not to send using --shallow-exclude. Cannot be non-empty if depth is
	 * nonzero.
	 */
	private List<String> deepenNotRefs = new ArrayList<>();

