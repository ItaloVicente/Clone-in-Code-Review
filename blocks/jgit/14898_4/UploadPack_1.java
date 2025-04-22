		AsyncRevObjectQueue q = walk.parseAny(notAdvertisedWants
		try {
			RevObject obj;
			while ((obj = q.next()) != null) {
				if (!(obj instanceof RevCommit))
					throw new PackProtocolException(MessageFormat.format(
						JGitText.get().wantNotValid
				walk.markStart((RevCommit) obj);
			}
		} catch (MissingObjectException notFound) {
			ObjectId id = notFound.getObjectId();
			throw new PackProtocolException(MessageFormat.format(
					JGitText.get().wantNotValid
		} finally {
			q.release();
