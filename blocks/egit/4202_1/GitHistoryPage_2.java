	private void markUninteresting(String prefix) throws IOException,
			MissingObjectException, IncorrectObjectTypeException {
		for (Entry<String, Ref> refEntry : input.getRepository()
				.getRefDatabase().getRefs(prefix).entrySet()) {
			Ref ref = refEntry.getValue();
			if (ref.isSymbolic())
				continue;
			Object refTarget = currentWalk
					.parseAny(ref.getLeaf().getObjectId());
			if (refTarget instanceof RevCommit)
				currentWalk.markUninteresting((RevCommit) refTarget);
		}
	}

