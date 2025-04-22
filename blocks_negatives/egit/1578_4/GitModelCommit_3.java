		super(parent);
		kind = direction;
		remoteCommit = commit;
		ancestorCommit = calculateAncestor(remoteCommit);

		RevCommit[] parents = remoteCommit.getParents();
		if (parents != null && parents.length > 0)
			baseCommit = remoteCommit.getParent(0);
		else {
			baseCommit = null;
		}
