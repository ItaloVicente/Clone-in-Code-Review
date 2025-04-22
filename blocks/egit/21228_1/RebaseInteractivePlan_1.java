	private PersonIdent getAuthor(AbbreviatedObjectId abbreviatedObjectId,
			RevWalk walk) {
		if (abbreviatedObjectId != null) {
			try {
				Collection<ObjectId> resolved = walk.getObjectReader().resolve(
						abbreviatedObjectId);
				if (resolved.size() == 1) {
					RevCommit commit = walk.parseCommit(resolved
							.iterator().next());
					return commit.getAuthorIdent();
				}
			} catch (IOException e) {
			}
		}
		return null;
	}

