	private void markStartRef(RevWalk walk, Ref ref) throws IOException,
			IncorrectObjectTypeException {
		try {
			Object refTarget = walk.parseAny(ref.getLeaf().getObjectId());
			if (refTarget instanceof RevCommit)
				walk.markStart((RevCommit) refTarget);
		} catch (MissingObjectException e) {
		}
