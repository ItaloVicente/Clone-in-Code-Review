
	private class CommitList extends ArrayList<RevCommit> {
		private RevCommit patch(RevCommit revCommit) {
			if (not) {
				if (revCommit.has(RevFlag.UNINTERESTING))
					revCommit.remove(RevFlag.UNINTERESTING);
				else
					revCommit.add(RevFlag.UNINTERESTING);
			}
			return revCommit;
		}

		@Override
		public boolean add(RevCommit revCommit) {
			return super.add(patch(revCommit));
		}
	}
