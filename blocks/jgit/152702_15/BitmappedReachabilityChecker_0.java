
		walk.reset();
		walk.sort(RevSort.TOPO);

		BitmapIndex repoBitmaps = walk.getObjectReader().getBitmapIndex();
		ReachedFilter reachedFilter = new ReachedFilter(repoBitmaps);
		walk.setRevFilter(reachedFilter);

		Iterator<RevCommit> startersIter = starters.iterator();
		while (startersIter.hasNext()) {
			walk.markStart(startersIter.next());
			while (walk.next() != null) {
				remainingTargets.removeIf(reachedFilter::isReachable);

				if (remainingTargets.isEmpty()) {
					return Optional.empty();
				}
