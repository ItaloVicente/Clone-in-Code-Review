
		if (local != null) {
			walk = new RevWalk(local);
			reachableCommits = new RevCommitList<RevCommit>();

			walk.carry(COMMON);
			walk.carry(REACHABLE);
			walk.carry(ADVERTISED);
		} else {
			walk = null;
			REACHABLE = null;
			COMMON = null;
			STATE = null;
			ADVERTISED = null;
		}
