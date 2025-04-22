	private void checkNotAdvertisedWants(Set<RevObject> notAdvertisedWants)
			throws MissingObjectException

		for (RevObject o : notAdvertisedWants) {
			if (!(o instanceof RevCommit)) {
				throw new PackProtocolException(MessageFormat.format(
						JGitText.get().wantNotValid
						notAdvertisedWants.iterator().next().name()));
			}
			walk.markStart((RevCommit) o);
		}

		for (ObjectId id : advertised) {
			try {
				walk.markUninteresting(walk.parseCommit(id));
			} catch (IncorrectObjectTypeException notCommit) {
				continue;
			}
		}

		RevCommit bad = walk.next();
		if (bad != null) {
			throw new PackProtocolException(MessageFormat.format(
					JGitText.get().wantNotValid
					bad.name()));
		}
		walk.reset();
	}

