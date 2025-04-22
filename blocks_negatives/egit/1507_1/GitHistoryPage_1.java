		final StringBuilder b = new StringBuilder();
		b.append(db.getDirectory().getParentFile().getName());
		if (currentWalk.getRevFilter() != RevFilter.ALL) {
			b.append(currentWalk.getRevFilter());
		}
		if (currentWalk.getTreeFilter() != TreeFilter.ALL) {
