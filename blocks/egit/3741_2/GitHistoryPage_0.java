	private void markStartAdditionalRefs() throws IOException {
		List<Ref> additionalRefs = input.getRepository().getRefDatabase()
				.getAdditionalRefs();
		for (Ref ref : additionalRefs)
			markStartRef(ref);
	}

	private void markStartRef(Ref ref) throws MissingObjectException,
			IOException, IncorrectObjectTypeException {
		Object refTarget = currentWalk.parseAny(ref.getLeaf().getObjectId());
		if (refTarget instanceof RevCommit)
			currentWalk.markStart((RevCommit) refTarget);
	}

