			try {
				final RevCommit o = walk.parseCommit(r.getObjectId());
				o.add(REACHABLE);
				reachableCommits.add(o);
			} catch (IOException readError) {
			}
