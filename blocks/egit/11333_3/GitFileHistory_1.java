	private void markStartAllRefs(RevWalk theWalk, String prefix)
			throws IOException, MissingObjectException,
			IncorrectObjectTypeException {
		for (Entry<String, Ref> refEntry : db.getRefDatabase().getRefs(prefix)
				.entrySet()) {
			Ref ref = refEntry.getValue();
			if (ref.isSymbolic())
				continue;
			markStartRef(theWalk, ref);
		}
	}

	private void markStartRef(RevWalk theWalk, Ref ref) throws IOException,
			IncorrectObjectTypeException {
		try {
			Object refTarget = theWalk.parseAny(ref.getLeaf().getObjectId());
			if (refTarget instanceof RevCommit)
				theWalk.markStart((RevCommit) refTarget);
		} catch (MissingObjectException e) {
		}
	}

