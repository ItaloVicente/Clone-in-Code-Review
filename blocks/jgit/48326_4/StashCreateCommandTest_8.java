		try (RevWalk walk = new RevWalk(db)) {
			RevCommit stashedIndex = stashedWorkTree.getParent(1);
			walk.parseBody(stashedIndex);
			walk.parseBody(stashedIndex.getTree());
			walk.parseBody(stashedIndex.getParent(0));
		}
