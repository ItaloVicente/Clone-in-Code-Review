	private void sendStatusReport(final boolean forClient,
			final Throwable unpackError, final Reporter out)
			throws IOException {
		if (unpackError != null) {
			if (forClient) {
				for (ReceiveCommand cmd : commands) {
