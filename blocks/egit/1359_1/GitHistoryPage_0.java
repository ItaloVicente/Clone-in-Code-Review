	private void markStartAllRefs(String prefix) throws IOException, MissingObjectException,
			IncorrectObjectTypeException {
		for (Entry<String, Ref> refEntry : db.getRefDatabase()
				.getRefs(prefix).entrySet()) {
			Ref ref = refEntry.getValue();
			if (ref.isSymbolic())
				continue;
			currentWalk.markStart(currentWalk.parseCommit(ref
					.getObjectId()));
		}
	}

