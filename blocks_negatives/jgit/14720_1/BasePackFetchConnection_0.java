		allowOfsDelta = cfg.allowOfsDelta;

		walk = new RevWalk(local);
		reachableCommits = new RevCommitList<RevCommit>();

		walk.carry(COMMON);
		walk.carry(REACHABLE);
		walk.carry(ADVERTISED);
