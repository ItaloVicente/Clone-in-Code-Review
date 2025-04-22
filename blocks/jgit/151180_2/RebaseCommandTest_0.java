		try (RevWalk revWalk = new RevWalk(db)) {
			RevCommit stashCommit = revWalk.parseCommit(stashId);
			List<DiffEntry> diffs = diffWorkingAgainstHead(stashCommit
					revWalk);
			return diffs;
		}
