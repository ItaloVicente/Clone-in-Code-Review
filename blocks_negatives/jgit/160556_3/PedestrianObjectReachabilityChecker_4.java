			Stream<RevObject> starters) throws MissingObjectException,
			IncorrectObjectTypeException, IOException {
		walk.reset();
		walk.sort(RevSort.TOPO);
		for (RevObject target : targets) {
			walk.markStart(target);
		}
