	public FileDiff[] getDiffs(RevCommit... parents) {
		RevWalk revWalk = new RevWalk(repository);
		TreeWalk treewalk = new TreeWalk(revWalk.getObjectReader());
		treewalk.setRecursive(true);
		treewalk.setFilter(TreeFilter.ANY_DIFF);
		FileDiff[] diffsResult = null;
		try {
			loadParents();
			diffsResult = FileDiff.compute(repository, treewalk, commit,
					parents, TreeFilter.ALL);
		} catch (IOException e) {
			diffsResult = new FileDiff[0];
		} finally {
			revWalk.release();
			treewalk.release();
		}
		return diffsResult;
	}

	private void loadParents() throws IOException {
		RevWalk revWalk = new RevWalk(repository);
		try {
			for (RevCommit parent : commit.getParents())
				revWalk.parseBody(parent);
		} finally {
			revWalk.release();
		}
	}

