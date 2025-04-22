	RevCommit(RevCommit orig) {
		super(orig.getId());
		this.buffer = orig.buffer;
		this.commitTime = orig.commitTime;
		this.flags = orig.flags;
		this.parents = orig.parents;
		this.tree = orig.tree;
		this.inDegree = orig.inDegree;
	}

