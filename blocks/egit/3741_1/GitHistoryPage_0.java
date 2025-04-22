	private void markStartAdditionalRefs() throws IOException {
		AnyObjectId head = resolveHead(input.getRepository(), true);
		if (head != null)
			markStartObj(head);
		List<Ref> additionalRefs = input.getRepository().getRefDatabase()
				.getAdditionalRefs();
		for (Ref ref : additionalRefs) {
			markStartRef(ref);
		}
	}

	private void markStartRef(Ref ref) throws MissingObjectException,
			IOException, IncorrectObjectTypeException {
		markStartObj(ref.getLeaf().getObjectId());
	}

	private void markStartObj(AnyObjectId id) throws MissingObjectException, IOException {
		Object refTarget = currentWalk.parseAny(id);
		if (refTarget instanceof RevCommit)
			currentWalk.markStart((RevCommit) refTarget);
	}

