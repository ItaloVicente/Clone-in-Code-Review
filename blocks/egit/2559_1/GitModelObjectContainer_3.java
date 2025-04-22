		if (commit != null) {
			baseCommit = commit;

			RevCommit[] parents = baseCommit.getParents();
			if (parents != null && parents.length > 0)
				remoteCommit = baseCommit.getParent(0);
			else {
				remoteCommit = null;
			}
		} else {
			baseCommit = null;
