	private void applyFilterToParents(RevCommit c)
			throws MissingObjectException
			IOException {
		outerLoop: for (RevCommit parent : c.parents) {

			while ((parent.flags & RevWalk.TREE_REV_FILTER_APPLIED) == 0) {

				RevCommit n = source.next();

				if (n != null) {
					pending.add(n);
				} else {
					break outerLoop;
				}

			}

		}
	}

	private RevCommit rewrite(RevCommit p) throws MissingObjectException
			IncorrectObjectTypeException
