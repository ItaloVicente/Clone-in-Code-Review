	private static void checkReachabilityByWalkingObjects(ObjectWalk walk,
			List<RevObject> wants, Set<ObjectId> reachableFrom) throws IOException {

		walk.sort(RevSort.TOPO);
		for (RevObject want : wants) {
			walk.markStart(want);
		}
		for (ObjectId have : reachableFrom) {
			RevObject o = walk.parseAny(have);
			walk.markUninteresting(o);

			RevObject peeled = walk.peel(o);
			if (peeled instanceof RevCommit) {
				walk.markUninteresting(((RevCommit) peeled).getTree());
			}
		}

		RevCommit commit = walk.next();
		if (commit != null) {
			throw new WantNotValidException(commit);
		}
		RevObject object = walk.nextObject();
		if (object != null) {
			throw new WantNotValidException(object);
		}
	}

