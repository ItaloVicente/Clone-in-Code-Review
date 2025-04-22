	public boolean isContainer() {
		return true;
	}

	/**
	 * @return base object ObjectId
	 */
	protected ObjectId getBaseObjectId() {
		return baseCommit != null ? baseCommit.getId() : zeroId();
	}

	/**
	 * @return remote object ObjectId
	 */
	protected ObjectId getRemoteObjectId() {
		return remoteCommit.getId();
	}

	private RevCommit calculateAncestor(RevCommit actual) throws IOException {
		RevWalk rw = new RevWalk(getRepository());
		rw.setRevFilter(RevFilter.MERGE_BASE);

		for (RevCommit parent : actual.getParents()) {
			RevCommit parentCommit = rw.parseCommit(parent.getId());
			rw.markStart(parentCommit);
		}

		rw.markStart(rw.parseCommit(actual.getId()));

		RevCommit result = rw.next();
		return result != null ? result : rw.parseCommit(ObjectId.zeroId());
	}

	private void getChildrenImpl() {
