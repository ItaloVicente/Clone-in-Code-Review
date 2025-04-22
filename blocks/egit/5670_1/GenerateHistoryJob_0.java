		this.walk = walk;
		allCommits = new SWTCommitList(control);
		allCommits.source(walk);
	}

	public RevWalk getWalk() {
		return walk;
