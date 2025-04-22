			markStart(walk, ref.getLeaf().getObjectId());
		}
		Ref head = repository.exactRef(Constants.HEAD);
		if (head != null) {
			markStart(walk, head.getLeaf().getObjectId());
		}
	}

	private void markStart(RevWalk walk, ObjectId id)
			throws IncorrectObjectTypeException, IOException {
		try {
			RevObject peeled = walk.peel(walk.parseAny(id));
			if (peeled instanceof RevCommit) {
				walk.markStart((RevCommit) peeled);
			}
		} catch (MissingObjectException e) {
