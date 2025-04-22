	private PlanElement createElement(RebaseTodoLine todoLine, RevWalk walk) {
		PersonIdent author = null;
		PersonIdent committer = null;

		RevCommit commit = loadCommit(todoLine.getCommit(), walk);
		if (commit != null) {
			author = commit.getAuthorIdent();
			committer = commit.getCommitterIdent();
		}

		PlanElement element = new PlanElement(todoLine, author, committer);
