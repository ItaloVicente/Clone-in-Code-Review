	@Nullable
	private static RevCommit next(ObjectWalk walker
			throws IOException {
		while (true) {
			try {
				return walker.next();
			} catch (MissingObjectException e) {
				if (!ignoreMissing) {
					throw e;
				}
			}
		}
	}

	@Nullable
	private static RevObject nextObject(ObjectWalk walker
			boolean ignoreMissing) throws IOException {
		while (true) {
			try {
				return walker.nextObject();
			} catch (MissingObjectException e) {
				if (!ignoreMissing) {
					throw e;
				}
			}
		}
	}

