	private void emitShallowOrUnshallow(RevCommit c
			IOConsumer<ObjectId> shallowFunc
			IOConsumer<ObjectId> unshallowFunc) throws IOException {

		if (isBoundary && !clientShallowCommits.contains(c)) {
			shallowFunc.accept(c.copy());
		}

		if (!isBoundary && clientShallowCommits.remove(c)) {
			unshallowFunc.accept(c.copy());
		}
	}

