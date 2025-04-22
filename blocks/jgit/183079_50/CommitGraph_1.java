
	class EmptyCommitGraph implements CommitGraph {

		private EmptyCommitGraph() {
		}

		@Override
		public int findGraphPosition(AnyObjectId commit) {
			return -1;
		}

		@Override
		public CommitData getCommitData(int graphPos) {
			return null;
		}

		@Override
		public ObjectId getObjectId(int graphPos) {
			return null;
		}

		@Override
		public long getCommitCnt() {
			return 0;
		}
	}
