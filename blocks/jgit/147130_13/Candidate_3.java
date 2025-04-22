	static final class HeadCandidate extends Candidate {

		private List<RevCommit> parents;

		HeadCandidate(Repository repo
				List<RevCommit> parents) {
			super(repo
			this.parents = parents;
		}

		@Override
		void beginResult(RevWalk rw) {
		}

		@Override
		int getParentCount() {
			return parents.size();
		}

		@Override
		RevCommit getParent(int idx) {
			return parents.get(idx);
		}

		@Override
		boolean has(RevFlag flag) {
		}

		@Override
		void add(RevFlag flag) {
		}

		@Override
		void remove(RevFlag flag) {
		}

		@Override
		int getTime() {
			return Integer.MAX_VALUE;
		}

		@Override
		PersonIdent getAuthor() {
			return new PersonIdent(JGitText.get().blameNotCommittedYet
		}
	}

