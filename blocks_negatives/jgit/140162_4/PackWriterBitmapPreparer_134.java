	private static final Comparator<RevCommit> ORDER_BY_REVERSE_TIMESTAMP = new Comparator<RevCommit>() {
		@Override
		public int compare(RevCommit a, RevCommit b) {
			return Integer.signum(b.getCommitTime() - a.getCommitTime());
		}
	};
