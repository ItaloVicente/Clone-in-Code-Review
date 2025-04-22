	@Override
	RevCommit next() throws MissingObjectException
			IncorrectObjectTypeException
		while (!ret.isEmpty()) {
			RevCommit commit = ret.remove();
			if ((commit.flags & mergeBaseAncestor) == 0) {
				return commit;
			}
		}
		return null;
	}

