	private boolean wantSatisfied(final RevObject want) throws IOException {
		if (want.has(SATISFIED))
			return true;

		if (!(want instanceof RevCommit)) {
			want.add(SATISFIED);
			return true;
		}

