			RevCommit commit = null;
			try {
				commit = walk.parseCommit(objectId);
			} catch (MissingObjectException e) {
			} catch (IncorrectObjectTypeException e) {
			}
			if (commit != null)
				add(commit);
