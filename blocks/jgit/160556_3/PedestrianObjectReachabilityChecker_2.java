			Stream<RevObject> starters) throws IOException {
		try {
			walk.reset();
			walk.sort(RevSort.TOPO);
			for (RevObject target : targets) {
				walk.markStart(target);
			}
