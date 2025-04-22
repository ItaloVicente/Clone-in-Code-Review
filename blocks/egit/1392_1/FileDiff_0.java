		if (commit.getParentCount() > 0)
			walk.reset(trees(commit));
		else {
			walk.reset();
			walk.addTree(new EmptyTreeIterator());
			walk.addTree(commit.getTree());
		}
