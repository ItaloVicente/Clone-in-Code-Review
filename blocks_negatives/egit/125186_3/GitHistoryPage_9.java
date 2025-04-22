	/**
	 * {@link RevWalk#markStart(RevCommit)} all refs with given prefix to mark
	 * start of graph traversal using currentWalker
	 * @param walk the walk to be configured
	 *
	 * @param prefix
	 *            prefix of refs to be marked
	 * @throws IOException
	 * @throws MissingObjectException
	 * @throws IncorrectObjectTypeException
	 */
	private void markStartAllRefs(RevWalk walk, String prefix) throws IOException,
			MissingObjectException, IncorrectObjectTypeException {
		for (Ref ref : input.getRepository().getRefDatabase()
				.getRefsByPrefix(prefix)) {
			if (ref.isSymbolic())
				continue;
			markStartRef(walk, ref);
		}
	}

	private void markStartAdditionalRefs(RevWalk walk) throws IOException {
		List<Ref> additionalRefs = input.getRepository().getRefDatabase()
				.getAdditionalRefs();
		for (Ref ref : additionalRefs)
			markStartRef(walk, ref);
	}

	private void markStartRef(RevWalk walk, Ref ref) throws IOException,
			IncorrectObjectTypeException {
		try {
			RevObject refTarget = walk.parseAny(ref.getLeaf().getObjectId());
			RevObject peeled = walk.peel(refTarget);
			if (peeled instanceof RevCommit)
				walk.markStart((RevCommit) peeled);
		} catch (MissingObjectException e) {
		}
	}

	private void markUninteresting(RevWalk walk, String prefix) throws IOException,
			MissingObjectException, IncorrectObjectTypeException {
		for (Ref ref : input.getRepository().getRefDatabase()
				.getRefsByPrefix(prefix)) {
			if (ref.isSymbolic())
				continue;
			Object refTarget = walk
					.parseAny(ref.getLeaf().getObjectId());
			if (refTarget instanceof RevCommit)
				walk.markUninteresting((RevCommit) refTarget);
		}
	}
