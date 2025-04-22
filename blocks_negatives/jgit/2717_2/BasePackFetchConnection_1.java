		for (final ObjectId id : have) {
			try {
				final RevCommit o = walk.parseCommit(id);
				o.add(REACHABLE);
				reachableCommits.add(o);
			} catch (IOException readError) {
			}
		}
