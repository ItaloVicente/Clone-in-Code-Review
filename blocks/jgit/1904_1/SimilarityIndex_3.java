	private static long countOf(long v) {
		return v & MAX_COUNT;
	}

	static class TableFullException extends Exception {
		private static final long serialVersionUID = 1L;
