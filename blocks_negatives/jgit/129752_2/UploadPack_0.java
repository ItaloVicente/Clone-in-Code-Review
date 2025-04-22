	/**
	 * (Possibly short) ref names, ancestors of which the client has asked us
	 * not to send using --shallow-exclude. Cannot be non-empty if depth is
	 * nonzero.
	 */
	private List<String> deepenNotRefs = new ArrayList<>();

