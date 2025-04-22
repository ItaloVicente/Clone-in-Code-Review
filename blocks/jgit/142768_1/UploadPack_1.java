	private static void checkReachabilityByWalkingObjects(RevWalk revWalk
			List<RevObject> wants

		ObjectWalk walk = revWalk.toObjectWalkWithSameObjects();

		walk.sort(RevSort.TOPO);
		for (RevObject want : wants) {
			walk.markStart(want);
		}
		for (ObjectId have : reachableFrom) {
			RevObject o = walk.parseAny(have);
			walk.markUninteresting(o);

			while (o instanceof RevTag) {
				o = ((RevTag) o).getObject();
				walk.parseHeaders(o);
			}
			if (o instanceof RevCommit) {
				walk.markUninteresting(((RevCommit) o).getTree());
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

