	private String createHostKeyLine(Collection<SshdSocketAddress> patterns
			PublicKey key) throws IOException {
		StringBuilder result = new StringBuilder();
		for (SshdSocketAddress address : patterns) {
			if (result.length() > 0) {
				result.append('
