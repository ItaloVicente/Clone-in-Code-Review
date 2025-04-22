	/**
	 * {@link RevWalk#markStart(RevCommit)} all refs with given prefix to mark
	 * start of graph traversal using currentWalker
	 *
	 * @param prefix
	 *            prefix of refs to be marked
	 * @throws IOException
	 * @throws MissingObjectException
	 * @throws IncorrectObjectTypeException
	 */
	private void markStartAllRefs(String prefix) throws IOException,
			MissingObjectException, IncorrectObjectTypeException {
		for (Entry<String, Ref> refEntry : input.getRepository()
				.getRefDatabase().getRefs(prefix).entrySet()) {
			Ref ref = refEntry.getValue();
			if (ref.isSymbolic())
				continue;
			currentWalk.markStart(currentWalk.parseCommit(ref.getObjectId()));
		}
