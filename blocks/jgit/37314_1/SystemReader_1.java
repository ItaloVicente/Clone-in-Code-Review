	private static final SystemReader DEFAULT;
	static {
		SystemReader r = new Default();
		r.init();
		DEFAULT = r;
	}

	private static class Default extends SystemReader {
